package com.example.composerecipeapp.ui.saved_recipe

import com.example.composerecipeapp.core.collectIn
import com.example.composerecipeapp.core.usecase.None
import com.example.composerecipeapp.core.viewmodel.AppEvent
import com.example.composerecipeapp.core.viewmodel.AppState
import com.example.composerecipeapp.core.viewmodel.BaseViewModel
import com.example.composerecipeapp.domain.usecases.LoadSavedRecipeUsecase
import com.example.composerecipeapp.ui.recipe_list.RecipeData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SaveRecipeViewModel @Inject constructor(
    val initialState: SaveRecipeState,
    val loadSavedRecipeUseCase: LoadSavedRecipeUsecase
) : BaseViewModel<SaveRecipeState, SaveRecipeEvent>(initialState = initialState) {

    override fun onEvent(event: SaveRecipeEvent, state: SaveRecipeState) {
        when (event) {
            is LoadRecipe -> loadRecipes()
        }
    }

    private fun loadRecipes() {
        withState {
            setState {
                this.onLoading()
            }
            loadSavedRecipeUseCase(params = None).collectIn(viewModelScope) {
                setState {
                    this.onSuccess(RecipeData(it))
                }
            }
        }
    }
}

data class SaveRecipeState(
    val isLoading: Boolean = false,
    val recipeData: RecipeData = RecipeData(emptyList())
) : AppState

fun SaveRecipeState.onLoading() = this.copy(isLoading = true)

fun SaveRecipeState.onSuccess(recipeData: RecipeData) =
    this.copy(isLoading = false,recipeData = recipeData)

interface SaveRecipeEvent : AppEvent
data class LoadRecipe(val loadAll: Boolean = true) : SaveRecipeEvent

