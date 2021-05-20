package com.recipeapp.view.viewmodel

import com.example.composerecipeapp.core.collectIn
import com.example.composerecipeapp.core.exception.Failure
import com.example.composerecipeapp.core.network.NetworkHandler
import com.example.composerecipeapp.core.viewmodel.BaseViewModel
import com.example.composerecipeapp.core.viewmodel.Event
import com.example.composerecipeapp.core.viewmodel.State
import com.example.composerecipeapp.data.repositories.RecipeRepository
import com.example.composerecipeapp.domain.usecases.GetRecipeDetailUsecase
import com.recipeapp.core.network.api_service.RecipeApi
import com.example.composerecipeapp.data.network.response.RecipeDetailResponse
import kotlinx.coroutines.flow.catch

class RecipeDetailViewModel(
    initialState: RecipeDetailState = RecipeDetailState()
) : BaseViewModel<RecipeDetailState, RecipeDetailEvent>(initialState) {

    private val repos =
        RecipeRepository(NetworkHandler.getRetrofitInstance().create(RecipeApi::class.java))
    var usecase = GetRecipeDetailUsecase(repos)

    private fun getRecipeDetailForId(id: String) {
        setState {
            copy(isLoading = true)
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
            copy(isLoading = false, recipeDetail = null)
        }
    }

    private fun handleSuccessResponse(recipeDetailResponse: RecipeDetailResponse) {
        setState {
            copy(isLoading = false, recipeDetail = recipeDetailResponse)
        }
    }

    override fun onEvent(event: RecipeDetailEvent) {
        when (event) {
            is LoadRecipeDetail -> getRecipeDetailForId(event.id)
        }
    }
}

open class RecipeDetailEvent : Event
data class LoadRecipeDetail(val id: String) : RecipeDetailEvent()

data class RecipeDetailState(
    val isLoading: Boolean = false,
    val recipeDetail: RecipeDetailResponse? = null,
    val failure: Failure? = null
) : State
