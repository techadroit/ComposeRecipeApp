package com.feature.saved.recipes.viewmodel

import com.state_manager.managers.StateEventManager
import com.state_manager.extensions.collectIn
import com.core.platform.usecase.None
import com.domain.common.pojo.RecipeModel
import com.domain.favourite.DeleteSavedRecipe
import com.domain.favourite.LoadSavedRecipeUsecase
import com.feature.saved.recipes.model.SavedData
import com.feature.saved.recipes.state.*
import com.state_manager.scopes.StateManagerCoroutineScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.zip
import javax.inject.Inject

@HiltViewModel
class SaveRecipeViewModel @Inject constructor(
    initialState: SaveRecipeState,
    val loadSavedRecipeUseCase: LoadSavedRecipeUsecase,
    val deleteSavedRecipe: DeleteSavedRecipe,
    val scope: StateManagerCoroutineScope,
) : StateEventManager<SaveRecipeState, SaveRecipeEvent>(initialState = initialState,scope) {

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
        }.collectIn(coroutineScope) {
            setState {
                onSuccess(SavedData(it))
            }
        }
    }

    private fun loadRecipes() {
        withState {
            setState {
                onLoading()
            }
            loadSavedRecipeUseCase(params = None).collectIn(coroutineScope) {
                setState {
                    onSuccess(SavedData(it))
                }
            }
        }
    }
}
