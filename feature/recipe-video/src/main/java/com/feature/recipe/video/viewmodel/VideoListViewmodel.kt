package com.feature.recipe.video.viewmodel

import com.state_manager.managers.StateEventManager
import com.core.platform.exception.Failure
import com.domain.common.pojo.VideoRecipeModel
import com.domain.recipe.video.SearchVideoRecipeUsecase
import com.state_manager.extensions.collectIn
import com.feature.recipe.video.state.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import javax.inject.Inject

@HiltViewModel
class VideoListViewmodel @Inject constructor(
    initialState: RecipeVideoState,
    val usecase: SearchVideoRecipeUsecase
) :
    StateEventManager<RecipeVideoState, VideoEvents>(initialState) {
    var page = 0

    private fun searchVideo(query: String, isPaginate: Boolean = false) {
        setState {
            onLoading(isPaginate = isPaginate)
                .setQuery(query)
        }
        usecase(SearchVideoRecipeUsecase.Param(query = query, offset = page)).catch {
            handleResponseFailure(this as Failure)
        }.collectIn(coroutineScope) {
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
