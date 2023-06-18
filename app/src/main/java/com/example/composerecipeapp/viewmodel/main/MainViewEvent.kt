package com.example.composerecipeapp.viewmodel.main

import com.state_manager.events.AppEvent

sealed class MainViewEvent : AppEvent

object LoadSettings : MainViewEvent()
