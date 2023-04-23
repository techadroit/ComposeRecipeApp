package com.example.composerecipeapp.viewmodel.recipe_list

import com.archerviewmodel.ArcherViewModel
import com.example.composerecipeapp.core.exception.Failure
import com.example.composerecipeapp.core.functional.collectIn
import com.example.composerecipeapp.core.network.api_service.RecipeApi
import com.example.composerecipeapp.domain.usecases.DeleteSavedRecipe
import com.example.composerecipeapp.domain.usecases.SaveRecipeUsecase
import com.example.composerecipeapp.domain.usecases.SearchRecipeUsecase
import com.example.composerecipeapp.ui.pojo.RecipeModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import javax.inject.Inject

@HiltViewModel
open class RecipeListViewmodel @Inject constructor(
    initialState: RecipeListState,
    val savedRecipeUsecase: SaveRecipeUsecase,
    val searchUsecase: SearchRecipeUsecase,
    val deleteSavedRecipe: DeleteSavedRecipe,
    val recipeService: RecipeApi
) :
    ArcherViewModel<RecipeListState, RecipeEvent>(initialState) {

    var page = 1
    private fun saveRecipe(recipeModel: RecipeModel) =
        savedRecipeUsecase(SaveRecipeUsecase.Param(recipeModel))
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
                setState { this.onLoading() }
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
        }
    }
}
