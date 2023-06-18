package com.feature.recipe.list.state

import com.state_manager.state.ArcherState

data class SearchState(val list: List<String> = emptyList()) : ArcherState
