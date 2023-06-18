package com.state_manager.viewmodel_test

import com.state_manager.events.AppEvent

data class CountingEvent(val counter: Int = 1) : AppEvent
