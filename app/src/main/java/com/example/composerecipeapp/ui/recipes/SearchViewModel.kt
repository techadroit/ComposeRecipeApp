package com.example.composerecipeapp.ui.recipes

import com.example.composerecipeapp.core.collectIn
import com.example.composerecipeapp.core.network.NetworkHandler
import com.example.composerecipeapp.core.viewmodel.BaseViewModel
import com.example.composerecipeapp.core.viewmodel.AppEvent
import com.example.composerecipeapp.core.viewmodel.AppState
import com.example.composerecipeapp.data.repositories.RecipeRepository
import com.example.composerecipeapp.domain.usecases.AutoCompleteUsecase
import kotlinx.coroutines.flow.catch

class SearchViewModel(initialState: SearchState = SearchState()) :
    BaseViewModel<SearchState, AppEvent>(initialState) {

    val usecase = AutoCompleteUsecase(RecipeRepository(NetworkHandler.getRecipeService()))

    override fun onEvent(event: AppEvent,state: SearchState) {
        when (event) {
            is SearchEvent -> searchForKeyword(event.searchText)
        }
    }

    private fun searchForKeyword(keyword: String) {
        usecase(keyword)
            .catch {  }
            .collectIn(viewModelScope) {
                setState {
                    copy(list = it)
                }
            }
    }
}

data class SearchState(val list: List<String> = emptyList()) : AppState

data class SearchEvent(val searchText: String) : AppEvent
