package com.example.composerecipeapp.ui.recipe_detail

import com.example.composerecipeapp.core.functional.collectIn
import com.example.composerecipeapp.core.exception.Failure
import com.example.composerecipeapp.core.viewmodel.ArcherViewModel
import com.example.composerecipeapp.data.network.response.RecipeDetailResponse
import com.example.composerecipeapp.data.network.response.toRecipeDetailModel
import com.example.composerecipeapp.domain.usecases.GetRecipeDetailUsecase
import com.example.composerecipeapp.domain.usecases.SimilarRecipeUsecase
import com.example.composerecipeapp.ui.pojo.RecipeModel
import com.example.composerecipeapp.viewmodel.recipe_detail.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.zip
import javax.inject.Inject

@HiltViewModel
class RecipeDetailViewModel @Inject constructor(
    initialState: RecipeDetailState,
    val usecase: GetRecipeDetailUsecase,
    val similarUsecase: SimilarRecipeUsecase
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
        recipeDetailResponse: RecipeDetailResponse,
        list: List<RecipeModel>
    ) {
        setState {
            this.onSuccessResponse(
                recipeDetail = recipeDetailResponse.toRecipeDetailModel(),
                recipeList = list
            )
        }
    }

    override fun onEvent(event: RecipeDetailEvent, state: RecipeDetailState) {
        when (event) {
            is LoadRecipeDetail -> getRecipeDetailForId(event.id)
        }
    }
}
