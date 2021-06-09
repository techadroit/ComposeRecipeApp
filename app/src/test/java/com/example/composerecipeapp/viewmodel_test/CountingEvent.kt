package com.example.composerecipeapp.viewmodel_test

import com.example.composerecipeapp.core.viewmodel.AppEvent

data class CountingEvent(val counter:Int = 1) : AppEvent
