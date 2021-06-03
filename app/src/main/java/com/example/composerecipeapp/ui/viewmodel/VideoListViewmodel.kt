package com.recipeapp.view.viewmodel

import com.example.composerecipeapp.core.Consumable
import com.example.composerecipeapp.core.collectIn
import com.example.composerecipeapp.core.exception.Failure
import com.example.composerecipeapp.core.network.NetworkHandler
import com.example.composerecipeapp.core.viewmodel.BaseViewModel
import com.example.composerecipeapp.core.viewmodel.AppEvent
import com.example.composerecipeapp.core.viewmodel.AppState
import com.example.composerecipeapp.data.repositories.RecipeRepository
import com.example.composerecipeapp.domain.usecases.SearchVideoRecipeUsecase
import com.example.composerecipeapp.ui.pojo.VideoRecipeModel
import com.example.composerecipeapp.ui.viewmodel.SideEffect
import com.example.composerecipeapp.util.QUERY
import com.recipeapp.core.network.api_service.RecipeApi
import com.recipeapp.data.network.response.VideoListResponses
import com.recipeapp.data.network.response.toRecipeModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import javax.inject.Inject

@HiltViewModel
class VideoListViewmodel @Inject constructor(initalState: RecipeVideoState, val usecase: SearchVideoRecipeUsecase) :
    BaseViewModel<RecipeVideoState, VideoEvents>(initalState) {
    var page = 0

    fun getVideoRecipe() {
        setState {
            this.onLoading()
        }
        searchVideo(QUERY)
    }

    private fun searchVideo(query: String, isPaginate: Boolean = false) {
        setState {
            this.onLoading(isPaginate = isPaginate)
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
            this.onSuccess(recipeModel = responses.toRecipeModel(),isPaginate = isPaginate)
        }
    }

    private fun handleResponseFailure(failure: Failure) {

    }

    override fun onEvent(event: VideoEvents,state: RecipeVideoState) {
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
) : AppState

interface VideoEvents : AppEvent

data class LoadVideos(var isPaginate: Boolean = false, val query: String = "Chicken") : VideoEvents

fun RecipeVideoState.onSuccess(recipeModel: List<VideoRecipeModel>,isPaginate: Boolean = false) =
    this.copy(data = this.data + recipeModel,isPaginate = isPaginate,isLoading = false)

fun RecipeVideoState.onLoading(isPaginate: Boolean = false) = this.copy(isPaginate = isPaginate,isLoading = true)
