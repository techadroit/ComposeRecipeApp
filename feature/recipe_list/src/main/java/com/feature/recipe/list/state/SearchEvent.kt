package com.feature.recipe.list.state

import com.archerviewmodel.events.ArcherEvent

sealed class SearchEvent : ArcherEvent
data class SearchTextEvent(val searchText: String) : SearchEvent()
