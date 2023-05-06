package com.feature.saved.recipes.viewmodel

import com.archerviewmodel.ArcherViewModel
import com.example.composerecipeapp.core.functional.collectIn
import com.core.platform.usecase.None
import com.domain.common.pojo.RecipeModel
import com.domain.favourite.DeleteSavedRecipe
import com.domain.favourite.LoadSavedRecipeUsecase
import com.feature.saved.recipes.model.SavedData
import com.feature.saved.recipes.state.*
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
            is RefreshViewEvent -> loadRecipes()
        }
    }

    private fun removeRecipe(recipeModel: RecipeModel) {
        deleteSavedRecipe(params = recipeModel.id).zip(loadSavedRecipeUseCase(params = None)) { _, result2 ->
            result2
        }.collectIn(viewModelScope) {
            setState {
                this.onSuccess(SavedData(it))
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
                    this.onSuccess(SavedData(it))
                }
            }
        }
    }
}
