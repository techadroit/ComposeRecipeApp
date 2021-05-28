package com.recipeapp.view.viewmodel

import com.example.composerecipeapp.core.collectIn
import com.example.composerecipeapp.core.exception.Failure
import com.example.composerecipeapp.core.network.NetworkHandler
import com.example.composerecipeapp.core.viewmodel.BaseViewModel
import com.example.composerecipeapp.core.viewmodel.AppEvent
import com.example.composerecipeapp.core.viewmodel.AppState
import com.example.composerecipeapp.data.repositories.RecipeRepository
import com.example.composerecipeapp.domain.usecases.GetRecipeDetailUsecase
import com.recipeapp.core.network.api_service.RecipeApi
import com.example.composerecipeapp.data.network.response.RecipeDetailResponse
import com.example.composerecipeapp.data.network.response.toRecipeDetailModel
import com.example.composerecipeapp.ui.pojo.RecipeDetailModel
import kotlinx.coroutines.flow.catch

class RecipeDetailViewModel(
    initialState: RecipeDetailState = RecipeDetailState()
) : BaseViewModel<RecipeDetailState, RecipeDetailEvent>(initialState) {

    private val repos =
        RecipeRepository(NetworkHandler.getRetrofitInstance().create(RecipeApi::class.java))
    var usecase = GetRecipeDetailUsecase(repos)

    private fun getRecipeDetailForId(id: String) {
        setState {
            this.onLoading()
        }
        usecase(GetRecipeDetailUsecase.Param(id = id))
            .catch {
                handleFailureResponse(this as Failure)
            }
            .collectIn(viewModelScope) {
                handleSuccessResponse(it)
            }
    }

    private fun handleFailureResponse(failure: Failure) {
        setState {
            this.onError(failure = failure)
        }
    }

    private fun handleSuccessResponse(recipeDetailResponse: RecipeDetailResponse) {
        setState {
            this.onSuccessResponse(recipeDetail = recipeDetailResponse.toRecipeDetailModel())
        }
    }

    override fun onEvent(event: RecipeDetailEvent,state: RecipeDetailState) {
        when (event) {
            is LoadRecipeDetail -> getRecipeDetailForId(event.id)
        }
    }
}

open class RecipeDetailEvent : AppEvent
data class LoadRecipeDetail(val id: String) : RecipeDetailEvent()

data class RecipeDetailState(
    val isLoading: Boolean = false,
    val recipeDetail: RecipeDetailModel? = null,
    val failure: Failure? = null
) : AppState

fun RecipeDetailState.onSuccessResponse(recipeDetail: RecipeDetailModel)
    = this.copy(isLoading = false,recipeDetail = recipeDetail)

fun RecipeDetailState.onError(failure: Failure) = this.copy(isLoading = false,failure = failure)

fun RecipeDetailState.onLoading() = this.copy(isLoading = true)
