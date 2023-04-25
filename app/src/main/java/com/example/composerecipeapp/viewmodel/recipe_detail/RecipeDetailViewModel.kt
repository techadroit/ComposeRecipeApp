package com.example.composerecipeapp.viewmodel.recipe_detail

import com.archerviewmodel.ArcherViewModel
import com.core.platform.exception.Failure
import com.example.composerecipeapp.core.functional.collectIn
import com.example.composerecipeapp.domain.usecases.DeleteSavedRecipe
import com.example.composerecipeapp.domain.usecases.GetRecipeDetailUsecase
import com.example.composerecipeapp.domain.usecases.SaveRecipeUsecase
import com.example.composerecipeapp.domain.usecases.SimilarRecipeUsecase
import com.example.composerecipeapp.ui.pojo.RecipeDetailModel
import com.example.composerecipeapp.ui.pojo.RecipeModel
import com.example.composerecipeapp.ui.pojo.toRecipe
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.zip
import javax.inject.Inject

@HiltViewModel
class RecipeDetailViewModel @Inject constructor(
    initialState: RecipeDetailState,
    val usecase: GetRecipeDetailUsecase,
    val similarUsecase: SimilarRecipeUsecase,
    val deleteSavedRecipe: DeleteSavedRecipe,
    val savedRecipeUsecase: SaveRecipeUsecase,
) : ArcherViewModel<RecipeDetailState, RecipeDetailEvent>(initialState) {

    private fun getRecipeDetailForId(id: String) {
        setState {
            this.onLoading()
        }
        usecase(GetRecipeDetailUsecase.Param(id = id))
            .zip(similarUsecase(SimilarRecipeUsecase.Param(id = id))) { r1, r2 ->
                Pair(r1, r2)
            }
            .catch {
                if (this is Failure)
                    handleFailureResponse(this as Failure)
            }
            .collectIn(viewModelScope) {
                handleSuccessResponse(it.first, it.second)
            }
    }

    private fun handleFailureResponse(failure: Failure) {
        setState {
            this.onError(failure = failure)
        }
    }

    private fun handleSuccessResponse(
        recipeDetailResponse: RecipeDetailModel,
        list: List<RecipeModel>
    ) {
        setState {
            this.onSuccessResponse(
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
            .collectIn(viewModelScope) {
                setState {
                    this.onRemoveFromSavedList()
                }
            }
    }

    private fun saveRecipe(recipeModel: RecipeModel) =
        savedRecipeUsecase(SaveRecipeUsecase.Param(recipeModel))
            .collectIn(viewModelScope) {
                setState {
                    this.onRecipeSaved()
                }
            }
}
