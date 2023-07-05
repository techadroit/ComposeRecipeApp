package com.feature.recipe.list.viewmodel

import com.domain.recipe.search.AutoCompleteUsecase
import com.feature.recipe.list.state.SearchEvent
import com.feature.recipe.list.state.SearchState
import com.feature.recipe.list.state.SearchTextEvent
import com.state_manager.extensions.collectIn
import com.state_manager.managers.StateEventManager
import com.state_manager.scopes.StateManagerCoroutineScope
import com.state_manager.scopes.StateManagerCoroutineScopeImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    initialState: SearchState,
    val useCase: AutoCompleteUsecase,
    val stateEventScope: StateManagerCoroutineScope = StateManagerCoroutineScopeImpl(),
) : StateEventManager<SearchState, SearchEvent>(initialState, stateEventScope) {

    override fun onEvent(event: SearchEvent, state: SearchState) {
        when (event) {
            is SearchTextEvent -> searchForKeyword(event.searchText)
        }
    }

    private fun searchForKeyword(keyword: String) {
        useCase(keyword)
            .catch { }
            .collectIn(coroutineScope) {
                setState {
                    copy(list = it)
                }
            }
    }
}
