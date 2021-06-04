package com.example.composerecipeapp.viewmodel.recipe_search

import com.example.composerecipeapp.core.viewmodel.AppState


data class SearchState(val list: List<String> = emptyList()) : AppState

