package com.example.composerecipeapp.viewmodel.recipe_search

import com.archerviewmodel.events.ArcherEvent

sealed class SearchEvent : ArcherEvent
data class SearchTextEvent(val searchText: String) : SearchEvent()
