package com.example.composerecipeapp.viewmodel.recipe_list

import com.archerviewmodel.ArcherViewModel
import com.core.platform.exception.Failure
import com.domain.common.pojo.RecipeModel
import com.domain.favourite.DeleteSavedRecipe
import com.domain.favourite.SaveRecipeUsecase
import com.domain.recipe.search.SearchRecipeUsecase
import com.example.composerecipeapp.core.functional.collectIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import javax.inject.Inject

@HiltViewModel
open class RecipeListViewmodel @Inject constructor(
    initialState: RecipeListState,
    val savedRecipeUsecase: SaveRecipeUsecase,
    val searchUsecase: SearchRecipeUsecase,
    val deleteSavedRecipe: DeleteSavedRecipe
) :
    ArcherViewModel<RecipeListState, RecipeEvent>(initialState) {

    var page = 1
    private fun saveRecipe(recipeModel: RecipeModel) =
        savedRecipeUsecase(com.domain.favourite.SaveRecipeUsecase.Param(recipeModel))
            .collectIn(viewModelScope) {
                setState {
                    this.onRecipeSaved(recipeModel.id)
                }
            }

    private fun deleteRecipe(recipeModel: RecipeModel) = deleteSavedRecipe(recipeModel.id)
        .collectIn(viewModelScope) {
            setState {
                this.onRecipeRemovedFromSavedList(recipeModel.id)
            }
        }

    private fun loadRecipes(query: String) =
        withState {
            if (!it.isLoading) {
                setState { this.onLoading(query = query) }
                searchRecipe(cuisine = query)
            }
        }

    private fun paginate(query: String) =
        withState {
            if (!it.isLoading) {
                page++
                setState { this.onLoading(true) }
                searchRecipe(cuisine = query, isPaginate = true)
            }
        }

    private fun searchRecipe(cuisine: String, isPaginate: Boolean = false) {

        val param = SearchRecipeUsecase.Param(cuisine = cuisine, offset = page)
        searchUsecase(param).catch { e ->
            handleFailure(e as Failure, isPaginate = isPaginate)
        }.collectIn(viewModelScope) {
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
            this.onError(failure = failure)
        }
    }

    private fun handleRecipeSearch(
        recipeList: List<RecipeModel>,
        isPaginate: Boolean,
        endOfItems: Boolean
    ) =
        setState {
            this.onRecipeLoad(isPaginate, endOfItems, recipeList)
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
