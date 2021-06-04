package com.example.composerecipeapp.ui.recipe_list

import com.example.composerecipeapp.core.Consumable
import com.example.composerecipeapp.core.collectIn
import com.example.composerecipeapp.core.exception.Failure
import com.example.composerecipeapp.core.viewmodel.BaseViewModel
import com.example.composerecipeapp.domain.usecases.SaveRecipeUsecase
import com.example.composerecipeapp.domain.usecases.SearchRecipeUsecase
import com.example.composerecipeapp.ui.pojo.RecipeModel
import com.example.composerecipeapp.viewmodel.recipe_list.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import javax.inject.Inject

@HiltViewModel
class RecipeListViewmodel @Inject constructor(
    initialState: RecipeListState, val savedRecipeUsecase: SaveRecipeUsecase,
    val searchUsecase: SearchRecipeUsecase
) :
    BaseViewModel<RecipeListState, RecipeEvent>(initialState) {

    var page = 1
    fun saveRecipe(recipeModel: RecipeModel) =
        savedRecipeUsecase(SaveRecipeUsecase.Param(recipeModel))
            .collectIn(viewModelScope) {
                onRecipeSaved()
            }

    private fun onRecipeSaved() {
        setState {
            copy(
                sideEffect = Consumable(SideEffect.OnSavedRecipe)
            )
        }
    }

    private fun loadRecipes(query: String) =
        withState {
            if (!it.isLoading) {
                setState { this.onLoading() }
                searchRecipe(query = query)
            }
        }

    private fun paginate(query: String) =
        withState {
            if (!it.isLoading) {
                page++
                setState { this.onLoading(true) }
                searchRecipe(query = query, isPaginate = true)
            }
        }

    private fun searchRecipe(query: String, isPaginate: Boolean = false) {
        searchUsecase(SearchRecipeUsecase.Param(query = query, offset = page))
            .catch { e ->
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
            is RemoveSavedRecipeEvent -> {
            }
            else -> {
            }
        }
    }
}
