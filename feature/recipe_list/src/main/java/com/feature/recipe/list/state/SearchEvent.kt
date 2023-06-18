package com.feature.recipe.list.state

import com.state_manager.events.AppEvent

sealed class SearchEvent : AppEvent
data class SearchTextEvent(val searchText: String) : SearchEvent()
