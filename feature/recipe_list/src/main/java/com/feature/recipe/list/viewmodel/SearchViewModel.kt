package com.feature.recipe.list.viewmodel

import com.archerviewmodel.ArcherViewModel
import com.domain.recipe.search.AutoCompleteUsecase
import com.example.composerecipeapp.core.functional.collectIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    initialState: com.feature.recipe.list.state.SearchState,
    val usecase: AutoCompleteUsecase
) :
    ArcherViewModel<com.feature.recipe.list.state.SearchState, com.feature.recipe.list.state.SearchEvent>(initialState) {

    override fun onEvent(event: com.feature.recipe.list.state.SearchEvent, state: com.feature.recipe.list.state.SearchState) {
        when (event) {
            is com.feature.recipe.list.state.SearchTextEvent -> searchForKeyword(event.searchText)
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
