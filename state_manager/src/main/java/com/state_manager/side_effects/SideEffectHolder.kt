package com.state_manager.side_effects

import kotlinx.coroutines.flow.StateFlow

interface SideEffectHolder<S: SideEffect> {

    val stateObservable: StateFlow<S?>

    val state: S?
        get() = stateObservable.value

    fun post(sideEffect: S)

    fun clearHolder()
}