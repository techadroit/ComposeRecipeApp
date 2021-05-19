package com.recipeapp.view.viewmodel

import com.example.composerecipeapp.core.Consumable
import com.example.composerecipeapp.core.collectIn
import com.example.composerecipeapp.core.exception.Failure
import com.example.composerecipeapp.core.network.NetworkHandler
import com.example.composerecipeapp.core.viewmodel.BaseViewModel
import com.example.composerecipeapp.core.viewmodel.Event
import com.example.composerecipeapp.core.viewmodel.State
import com.example.composerecipeapp.data.repositories.RecipeRepository
import com.example.composerecipeapp.domain.usecases.SearchVideoRecipeUsecase
import com.example.composerecipeapp.ui.pojo.VideoRecipeModel
import com.example.composerecipeapp.util.QUERY
import com.recipeapp.core.network.api_service.RecipeApi
import com.recipeapp.data.network.response.VideoListResponses
import com.recipeapp.data.network.response.toRecipeModel
import kotlinx.coroutines.flow.catch

class VideoListViewmodel(initalState: RecipeVideoState = RecipeVideoState()) :
    BaseViewModel<RecipeVideoState, VideoEvents>(initalState) {
    var page = 0
    val repos =
        RecipeRepository(NetworkHandler.getRetrofitInstance().create(RecipeApi::class.java))
    val usecase = SearchVideoRecipeUsecase(repos)


    fun getVideoRecipe() {
        setState {
            copy(isLoading = true)
        }
        searchVideo(QUERY)
    }

    private fun searchVideo(query: String, isPaginate: Boolean = false) {
        setState {
            copy(isLoading = true,isPaginate = isPaginate)
        }
        usecase(SearchVideoRecipeUsecase.Param(query = query, offset = page)).catch {
            handleResponseFailure(this as Failure)
        }.collectIn(viewModelScope) {
            handleVideoResponse(it, isPaginate = isPaginate)
        }.also {
            page++
        }
    }

    private fun handleVideoResponse(responses: VideoListResponses, isPaginate: Boolean = false) {
        setState {
            copy(
                data = this.data + responses.toRecipeModel(),
                isPaginate = isPaginate,
                isLoading = false
            )
        }
    }

    private fun handleResponseFailure(failure: Failure) {

    }

    override fun onEvent(event: VideoEvents) {
        when (event) {
            is LoadVideos -> searchVideo(event.query, event.isPaginate)
        }
    }
}

data class RecipeVideoState(
    val data: List<VideoRecipeModel> = emptyList(),
    val sideEffect: Consumable<SideEffect>? = null,
    val isLoading: Boolean = false,
    val isPaginate: Boolean = false
) : State

interface VideoEvents : Event

data class LoadVideos(var isPaginate: Boolean = false, val query: String = "Chicken") : VideoEvents
