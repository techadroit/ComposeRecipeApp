package com.state_manager.handler

import com.state_manager.side_effects.SideEffect
import kotlinx.coroutines.flow.StateFlow

interface SideEffectHandler<S:SideEffect> {

    fun postSideEffect(sideEffect:S)

    fun onSideEffect(): StateFlow<S?>
}