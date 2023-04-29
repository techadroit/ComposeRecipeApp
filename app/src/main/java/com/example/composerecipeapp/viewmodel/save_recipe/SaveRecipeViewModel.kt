package com.example.composerecipeapp.viewmodel.save_recipe

import com.archerviewmodel.ArcherViewModel
import com.example.composerecipeapp.core.functional.collectIn
import com.core.platform.usecase.None
import com.domain.favourite.DeleteSavedRecipe
import com.domain.favourite.LoadSavedRecipeUsecase
import com.domain.common.pojo.RecipeModel
import com.example.composerecipeapp.viewmodel.recipe_list.RecipeData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.zip
import javax.inject.Inject

@HiltViewModel
class SaveRecipeViewModel @Inject constructor(
    val initialState: SaveRecipeState,
    val loadSavedRecipeUseCase: com.domain.favourite.LoadSavedRecipeUsecase,
    val deleteSavedRecipe: com.domain.favourite.DeleteSavedRecipe
) : ArcherViewModel<SaveRecipeState, SaveRecipeEvent>(initialState = initialState) {

    override fun onEvent(event: SaveRecipeEvent, state: SaveRecipeState) {
        when (event) {
            is LoadRecipe -> loadRecipes()
            is RemoveRecipe -> removeRecipe(event.recipeModel)
            is RefreshViewEvent -> loadRecipes()
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
