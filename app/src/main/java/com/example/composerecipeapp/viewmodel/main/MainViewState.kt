package com.example.composerecipeapp.viewmodel.main

import com.state_manager.state.ArcherState

data class MainViewState(val isDarkModeOn: Boolean = false, val showLandingScreen: Boolean ? = null) : ArcherState
