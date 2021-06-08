package com.example.composerecipeapp.viewmodel.recipe_search

import com.example.composerecipeapp.core.viewmodel.AppEvent


sealed class SearchEvent : AppEvent
data class SearchTextEvent(val searchText: String) : SearchEvent()

