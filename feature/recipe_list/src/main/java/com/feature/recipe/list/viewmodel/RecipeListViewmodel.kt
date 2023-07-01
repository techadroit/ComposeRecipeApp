package com.feature.recipe.list.viewmodel

import com.state_manager.managers.StateEventManager
import com.core.platform.exception.Failure
import com.domain.common.pojo.RecipeModel
import com.domain.favourite.DeleteSavedRecipe
import com.domain.favourite.SaveRecipeUsecase
import com.domain.recipe.search.SearchRecipeUsecase
import com.state_manager.extensions.collectIn
import com.feature.recipe.list.state.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import javax.inject.Inject

@HiltViewModel
open class RecipeListViewmodel @Inject constructor(
    initialState: RecipeListState,
    val savedRecipeUseCase: SaveRecipeUsecase,
    val searchUseCase: SearchRecipeUsecase,
    val deleteSavedRecipe: DeleteSavedRecipe,
    val stateManagerCoroutineScope: StateManagerCoroutineScope
) :
    StateEventManager<RecipeListState, RecipeEvent>(initialState,stateManagerCoroutineScope) {

    var page = 1
    private fun saveRecipe(recipeModel: RecipeModel) =
        savedRecipeUseCase(SaveRecipeUsecase.Param(recipeModel))
            .collectIn(coroutineScope) {
                setState {
                    onRecipeSaved(recipeModel.id)
                }
                postSideEffect { OnSavedRecipe }
            }

    private fun deleteRecipe(recipeModel: RecipeModel) = deleteSavedRecipe(recipeModel.id)
        .collectIn(coroutineScope) {
            setState {
                onRecipeRemovedFromSavedList(recipeModel.id)
            }
        }

    private fun loadRecipes(query: String) =
        withState {
            if (!it.isLoading) {
                setState { onLoading(query = query) }
                searchRecipe(cuisine = query)
            }
        }

    private fun paginate(query: String) =
        withState {
            if (!it.isLoading) {
                page++
                setState { onLoading(true) }
                searchRecipe(cuisine = query, isPaginate = true)
            }
        }

    private fun searchRecipe(cuisine: String, isPaginate: Boolean = false) {

        val param = SearchRecipeUsecase.Param(cuisine = cuisine, offset = page)
        searchUseCase(param).catch { e ->
            handleFailure(e as Failure, isPaginate = isPaginate)
        }.collectIn(coroutineScope) {
            handleRecipeSearch(it.first, isPaginate, it.second)
        }
    }

    private fun refreshRecipeList() {
        page = 1
        withState {
            val selectedQuery = it.selectedQuery ?: return@withState
            setState {
                RecipeListState()
            }
            loadRecipes(selectedQuery)
        }
    }

    private fun handleFailure(failure: Failure, isPaginate: Boolean = false) {
        setState {
            onError(failure = failure)
        }
    }

    private fun handleRecipeSearch(
        recipeList: List<RecipeModel>,
        isPaginate: Boolean,
        endOfItems: Boolean
    ) =
        setState {
            onRecipeLoad(isPaginate, endOfItems, recipeList)
        }

    override fun onEvent(event: RecipeEvent, state: RecipeListState) {
        when (event) {
            is LoadRecipes -> if (event.isPaginate) paginate(event.query) else loadRecipes(event.query)
            is SaveRecipeEvent -> saveRecipe(event.recipeModel)
            is RemoveSavedRecipeEvent -> deleteRecipe(event.recipeModel)
            is RefreshRecipeList -> refreshRecipeList()
        }
    }
}
