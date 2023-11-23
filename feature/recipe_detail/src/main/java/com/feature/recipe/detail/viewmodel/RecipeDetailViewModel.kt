package com.feature.recipe.detail.viewmodel

import androidx.lifecycle.viewModelScope
import com.state_manager.managers.StateEventManager
import com.core.platform.exception.Failure
import com.domain.common.pojo.RecipeDetailModel
import com.domain.common.pojo.RecipeModel
import com.domain.common.pojo.toRecipe
import com.domain.favourite.DeleteSavedRecipe
import com.domain.favourite.SaveRecipeUsecase
import com.domain.recipe.SimilarRecipeUsecase
import com.state_manager.extensions.collectIn
import com.domain.recipe.GetRecipeDetailUsecase
import com.feature.common.IoDispatcher
import com.feature.recipe.detail.state.*
import com.state_manager.extensions.collectInScope
import com.state_manager.scopes.StateManagerCoroutineScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.zip
import javax.inject.Inject

@HiltViewModel
class RecipeDetailViewModel @Inject constructor(
    initialState: RecipeDetailState,
    val useCase: GetRecipeDetailUsecase,
    val similarUseCase: SimilarRecipeUsecase,
    val deleteSavedRecipe: DeleteSavedRecipe,
    val savedRecipeUseCase: SaveRecipeUsecase,
    val stateManagerCoroutineScope: StateManagerCoroutineScope,
    @IoDispatcher val dispatcher: CoroutineDispatcher
) : StateEventManager<RecipeDetailState, RecipeDetailEvent>(initialState) {

    private fun getRecipeDetailForId(id: String) {
        setState {
            onLoading()
        }
        useCase(GetRecipeDetailUsecase.Param(id = id))
            .zip(similarUseCase(SimilarRecipeUsecase.Param(id = id))) { r1, r2 ->
                Pair(r1, r2)
            }
            .flowOn(dispatcher)
            .catch {
                if (this is Failure)
                    handleFailureResponse(this as Failure)
            }
            .collectIn(stateManagerCoroutineScope) {
                handleSuccessResponse(it.first, it.second)
            }
    }

    private fun handleFailureResponse(failure: Failure) {
        setState {
            onError(failure = failure)
        }
    }

    private fun handleSuccessResponse(
        recipeDetailResponse: RecipeDetailModel,
        list: List<RecipeModel>
    ) {
        setState {
            onSuccessResponse(
                recipeDetail = recipeDetailResponse,
                recipeList = list
            )
        }
    }

    override fun onEvent(event: RecipeDetailEvent, state: RecipeDetailState) {
        when (event) {
            is LoadRecipeDetail -> getRecipeDetailForId(event.id)
            is RemoveRecipe -> state.recipeDetail?.let { removeRecipe(it.id) }
            is SaveRecipe -> state.recipeDetail?.let { saveRecipe(it.toRecipe()) }
        }
    }

    private fun removeRecipe(recipeId: Int) {
        deleteSavedRecipe(params = recipeId)
            .collectIn(stateManagerCoroutineScope) {
                setState {
                    onRemoveFromSavedList()
                }
            }
    }

    private fun saveRecipe(recipeModel: RecipeModel) =
        savedRecipeUseCase(SaveRecipeUsecase.Param(recipeModel))
            .collectIn(stateManagerCoroutineScope) {
                setState {
                    onRecipeSaved()
                }
            }
}
