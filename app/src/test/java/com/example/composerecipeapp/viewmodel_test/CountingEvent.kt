package com.example.composerecipeapp.viewmodel_test

import com.archerviewmodel.events.ArcherEvent

data class CountingEvent(val counter: Int = 1) : ArcherEvent
