package com.feature.recipe.list.state

import com.state_manager.state.AppState

data class SearchState(val list: List<String> = emptyList()) : AppState
