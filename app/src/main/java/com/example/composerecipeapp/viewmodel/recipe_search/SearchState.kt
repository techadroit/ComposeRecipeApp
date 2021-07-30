package com.example.composerecipeapp.viewmodel.recipe_search

import com.archerviewmodel.state.ArcherState

data class SearchState(val list: List<String> = emptyList()) : ArcherState
