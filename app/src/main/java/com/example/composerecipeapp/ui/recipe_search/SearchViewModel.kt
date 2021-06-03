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
    BaseViewModel<SearchState, AppEvent>(initialState) {


    override fun onEvent(event: AppEvent, state: SearchState) {
        when (event) {
            is SearchEvent -> searchForKeyword(event.searchText)
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

data class SearchEvent(val searchText: String) : AppEvent
