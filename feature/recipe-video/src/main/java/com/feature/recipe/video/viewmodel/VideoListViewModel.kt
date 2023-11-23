package com.feature.recipe.video.viewmodel

import androidx.lifecycle.viewModelScope
import com.core.platform.exception.Failure
import com.core.platform.exception.toFailure
import com.domain.common.pojo.VideoRecipeModel
import com.domain.recipe.video.SearchVideoRecipeUsecase
import com.feature.common.IoDispatcher
import com.feature.common.ui.error_screen.ErrorSideEffect
import com.feature.recipe.video.state.LoadVideos
import com.feature.recipe.video.state.RecipeVideoState
import com.feature.recipe.video.state.RefreshVideoScreen
import com.feature.recipe.video.state.VideoEvents
import com.feature.recipe.video.state.onFailure
import com.feature.recipe.video.state.onLoading
import com.feature.recipe.video.state.onSuccess
import com.feature.recipe.video.state.setQuery
import com.feature.recipe.video.state.showLoading
import com.state_manager.extensions.collectInScope
import com.state_manager.managers.StateEventManager
import com.state_manager.scopes.StateManagerCoroutineScope
import com.state_manager.test.TestStateManagerScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

@HiltViewModel
class VideoListViewModel @Inject constructor(
    initialState: RecipeVideoState,
    val searchVideoUseCase: SearchVideoRecipeUsecase,
    val managerScope: StateManagerCoroutineScope,
    @IoDispatcher val dispatcher: CoroutineDispatcher
) :
    StateEventManager<RecipeVideoState, VideoEvents>(initialState,managerScope) {
    var page = 0

    init {
        dispatch(LoadVideos())
    }

    private fun searchVideo(query: String, isPaginate: Boolean = false) {
        setState {
            onLoading(isPaginate = isPaginate)
                .setQuery(query)
        }
        searchVideoUseCase(SearchVideoRecipeUsecase.Param(query = query, offset = page))
            .flowOn(dispatcher)
            .catch {
                handleResponseFailure(it.toFailure())
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
                .onLoading(isLoading = false)
        }
    }

    private fun handleResponseFailure(failure: Failure) {
        setState {
            showLoading(false)
                .onFailure(failure)
        }
    }

    private fun refresh() {
        page = 0
        val selectedQuery =
            currentState.query ?: throw NullPointerException("No Selected Query found in the state")

        setState {
            RecipeVideoState().onLoading(false)
        }
        searchVideo(selectedQuery, false)

    }

    override fun onEvent(event: VideoEvents, state: RecipeVideoState) {
        when (event) {
            is LoadVideos -> searchVideo(event.query, event.isPaginate)
            is RefreshVideoScreen -> refresh()
        }
    }
}
