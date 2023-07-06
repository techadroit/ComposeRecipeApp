package com.feature.recipe.video.viewmodel

import androidx.lifecycle.viewModelScope
import com.core.platform.exception.Failure
import com.domain.common.pojo.VideoRecipeModel
import com.domain.recipe.video.SearchVideoRecipeUsecase
import com.feature.common.IoDispatcher
import com.feature.recipe.video.state.LoadVideos
import com.feature.recipe.video.state.RecipeVideoState
import com.feature.recipe.video.state.RefreshVideoScreen
import com.feature.recipe.video.state.VideoEvents
import com.feature.recipe.video.state.onLoading
import com.feature.recipe.video.state.onSuccess
import com.feature.recipe.video.state.setQuery
import com.state_manager.extensions.collectInScope
import com.state_manager.managers.StateEventManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

@HiltViewModel
class VideoListViewmodel @Inject constructor(
    initialState: RecipeVideoState,
    val usecase: SearchVideoRecipeUsecase,
    @IoDispatcher val dispatcher: CoroutineDispatcher
) :
    StateEventManager<RecipeVideoState, VideoEvents>(initialState) {
    var page = 0

    private fun searchVideo(query: String, isPaginate: Boolean = false) {
        setState {
            onLoading(isPaginate = isPaginate)
                .setQuery(query)
        }
        usecase(SearchVideoRecipeUsecase.Param(query = query, offset = page))
            .flowOn(dispatcher)
            .catch {
                handleResponseFailure(this as Failure)
            }.collectInScope(viewModelScope) {
                handleVideoResponse(it, isPaginate = isPaginate)
            }.also {
                page++
            }
    }

    private fun handleVideoResponse(
        responses: List<VideoRecipeModel>,
        isPaginate: Boolean = false
    ) {
        setState {
            this.onSuccess(recipeModel = responses, isPaginate = isPaginate)
        }
    }

    private fun handleResponseFailure(failure: Failure) {
    }

    private fun refresh() {
        page = 0
        val selectedQuery =
            currentState.query ?: throw NullPointerException("No Selected Query found in the state")

        setState {
            RecipeVideoState().onLoading(false)
        }
//        searchVideo(selectedQuery,false)

//        setState {
//            onLoading(isPaginate = isPaginate)
//                .setQuery(selectedQuery)
//        }
//        usecase(SearchVideoRecipeUsecase.Param(query = selectedQuery, offset = page))
//            .flowOn(dispatcher)
//            .catch {
//                handleResponseFailure(this as Failure)
//            }.collectInScope(viewModelScope) {
//                handleVideoResponse(it, isPaginate = false)
//            }.also {
//                page++
//            }
    }

    override fun onEvent(event: VideoEvents, state: RecipeVideoState) {
        when (event) {
            is LoadVideos -> searchVideo(event.query, event.isPaginate)
            is RefreshVideoScreen -> refresh()
        }
    }
}
