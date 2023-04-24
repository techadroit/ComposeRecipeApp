package com.example.composerecipeapp.viewmodel.recipe_video

import com.archerviewmodel.ArcherViewModel
import com.example.composerecipeapp.core.exception.Failure
import com.example.composerecipeapp.core.functional.collectIn
import com.example.composerecipeapp.domain.usecases.SearchVideoRecipeUsecase
import com.example.composerecipeapp.ui.pojo.VideoRecipeModel
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

    private fun searchVideo(query: String, isPaginate: Boolean = false) {
        setState {
            onLoading(isPaginate = isPaginate)
                .setQuery(query)
        }
        usecase(SearchVideoRecipeUsecase.Param(query = query, offset = page)).catch {
            handleResponseFailure(this as Failure)
        }.collectIn(viewModelScope) {
            handleVideoResponse(it, isPaginate = isPaginate)
        }.also {
            page++
        }
    }

    private fun handleVideoResponse(responses: List<VideoRecipeModel>, isPaginate: Boolean = false) {
        setState {
            this.onSuccess(recipeModel = responses, isPaginate = isPaginate)
        }
    }

    private fun handleResponseFailure(failure: Failure) {
    }

    private fun refresh(){
        page=0
        val selectedQuery = currentState.query ?: throw NullPointerException("No Selected Query found in the state")
        setState {
            RecipeVideoState()
        }
        searchVideo(selectedQuery,false)
    }

    override fun onEvent(event: VideoEvents, state: RecipeVideoState) {
        when (event) {
            is LoadVideos -> searchVideo(event.query, event.isPaginate)
            is RefreshVideoScreen -> refresh()
        }
    }
}
