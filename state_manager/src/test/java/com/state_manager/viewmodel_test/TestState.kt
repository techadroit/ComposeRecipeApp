package com.state_manager.viewmodel_test

import com.state_manager.side_effects.SideEffect
import com.state_manager.state.AppState

data class TestState(val counter: Int = 0, val isSetting: Boolean= false) : AppState

data class SuccessUpdate(val counter: Int = 0) : SideEffect