package com.feature.recipe.list.viewmodel

import androidx.lifecycle.viewModelScope
import com.domain.recipe.search.AutoCompleteUsecase
import com.feature.common.IoDispatcher
import com.feature.recipe.list.state.SearchEvent
import com.feature.recipe.list.state.SearchState
import com.feature.recipe.list.state.SearchTextEvent
import com.state_manager.extensions.collectIn
import com.state_manager.extensions.collectInScope
import com.state_manager.managers.StateEventManager
import com.state_manager.scopes.StateManagerCoroutineScope
import com.state_manager.scopes.StateManagerCoroutineScopeImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    initialState: SearchState,
    val useCase: AutoCompleteUsecase,
    @IoDispatcher val flowDispatcher: CoroutineDispatcher
) : StateEventManager<SearchState, SearchEvent>(initialState) {

    override fun onEvent(event: SearchEvent, state: SearchState) {
        when (event) {
            is SearchTextEvent -> searchForKeyword(event.searchText)
        }
    }

    private fun searchForKeyword(keyword: String) {
        useCase(keyword)
            .flowOn(flowDispatcher)
            .collectInScope(viewModelScope) {
                setState {
                    copy(list = it)
                }
            }
    }
}
