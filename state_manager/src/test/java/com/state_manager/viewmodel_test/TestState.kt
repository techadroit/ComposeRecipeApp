package com.state_manager.viewmodel_test

import com.state_manager.state.AppState

data class TestState(val counter: Int = 0, val isSetting: Boolean= false) : AppState