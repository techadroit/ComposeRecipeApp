package com.example.composerecipeapp.viewmodel.save_recipe

import com.archerviewmodel.ArcherViewModel
import com.example.composerecipeapp.core.functional.collectIn
import com.example.composerecipeapp.core.usecase.None
import com.example.composerecipeapp.domain.usecases.DeleteSavedRecipe
import com.example.composerecipeapp.domain.usecases.LoadSavedRecipeUsecase
import com.example.composerecipeapp.ui.pojo.RecipeModel
import com.example.composerecipeapp.viewmodel.recipe_list.RecipeData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.zip
import javax.inject.Inject

@HiltViewModel
class SaveRecipeViewModel @Inject constructor(
    val initialState: SaveRecipeState,
    val loadSavedRecipeUseCase: LoadSavedRecipeUsecase,
    val deleteSavedRecipe: DeleteSavedRecipe
) : ArcherViewModel<SaveRecipeState, SaveRecipeEvent>(initialState = initialState) {

    override fun onEvent(event: SaveRecipeEvent, state: SaveRecipeState) {
        when (event) {
            is LoadRecipe -> loadRecipes()
            is RemoveRecipe -> removeRecipe(event.recipeModel)
        }
    }

    private fun removeRecipe(recipeModel: RecipeModel) {
        deleteSavedRecipe(params = recipeModel.id).zip(loadSavedRecipeUseCase(params = None)) { _, result2 ->
            result2
        }.collectIn(viewModelScope) {
            setState {
                this.onSuccess(RecipeData(it))
            }
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
