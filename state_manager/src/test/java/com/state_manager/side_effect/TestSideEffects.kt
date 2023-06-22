package com.state_manager.side_effect

import com.state_manager.side_effects.SideEffect

sealed class TestSideEffects : SideEffect

object ShowToast: TestSideEffects()
object ShowDialog: TestSideEffects()