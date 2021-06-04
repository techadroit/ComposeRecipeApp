package com.example.composerecipeapp.viewmodel.save_recipe

import com.example.composerecipeapp.core.functional.collectIn
import com.example.composerecipeapp.core.usecase.None
import com.example.composerecipeapp.core.viewmodel.BaseViewModel
import com.example.composerecipeapp.domain.usecases.LoadSavedRecipeUsecase
import com.example.composerecipeapp.viewmodel.recipe_list.RecipeData
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
