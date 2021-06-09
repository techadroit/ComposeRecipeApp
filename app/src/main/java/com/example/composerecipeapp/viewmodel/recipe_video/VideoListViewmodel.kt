package com.example.composerecipeapp.viewmodel.recipe_video

import com.example.composerecipeapp.core.functional.collectIn
import com.example.composerecipeapp.core.exception.Failure
import com.example.composerecipeapp.core.viewmodel.ArcherViewModel
import com.example.composerecipeapp.domain.usecases.SearchVideoRecipeUsecase
import com.example.composerecipeapp.util.QUERY
import com.recipeapp.data.network.response.VideoListResponses
import com.recipeapp.data.network.response.toRecipeModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import javax.inject.Inject

@HiltViewModel
class VideoListViewmodel @Inject constructor(
    initalState: RecipeVideoState,
    val usecase: SearchVideoRecipeUsecase
) :
    ArcherViewModel<RecipeVideoState, VideoEvents>(initalState) {
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
            this.onSuccess(recipeModel = responses.toRecipeModel(), isPaginate = isPaginate)
        }
    }

    private fun handleResponseFailure(failure: Failure) {

    }

    override fun onEvent(event: VideoEvents, state: RecipeVideoState) {
        when (event) {
            is LoadVideos -> searchVideo(event.query, event.isPaginate)
        }
    }
}
