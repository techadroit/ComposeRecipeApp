package com.example.composerecipeapp.ui.recipe_search

import com.example.composerecipeapp.core.collectIn
import com.example.composerecipeapp.core.viewmodel.AppEvent
import com.example.composerecipeapp.core.viewmodel.AppState
import com.example.composerecipeapp.core.viewmodel.BaseViewModel
import com.example.composerecipeapp.domain.usecases.AutoCompleteUsecase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    initialState: SearchState,
    val usecase: AutoCompleteUsecase
) :
    BaseViewModel<SearchState, SearchEvent>(initialState) {


    override fun onEvent(event: SearchEvent, state: SearchState) {
        when (event) {
            is SearchTextEvent -> searchForKeyword(event.searchText)
        }
    }

    private fun searchForKeyword(keyword: String) {
        usecase(keyword)
            .catch { }
            .collectIn(viewModelScope) {
                setState {
                    copy(list = it)
                }
            }
    }
}

data class SearchState(val list: List<String> = emptyList()) : AppState

interface SearchEvent : AppEvent
data class SearchTextEvent(val searchText: String) : SearchEvent
