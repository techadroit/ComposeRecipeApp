package com.feature.recipe.list.state

import com.archerviewmodel.state.ArcherState

data class SearchState(val list: List<String> = emptyList()) : ArcherState
