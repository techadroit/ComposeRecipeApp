package com.example.composerecipeapp.viewmodel.main

import com.state_manager.state.AppState

data class MainViewState(val isDarkModeOn: Boolean? = false, val showLandingScreen: Boolean ? = null) : AppState
