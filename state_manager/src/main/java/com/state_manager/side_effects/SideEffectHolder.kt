package com.state_manager.side_effects

import com.state_manager.extensions.Consumable
import kotlinx.coroutines.flow.StateFlow

interface SideEffectHolder<S: SideEffect> {

    val stateObservable: StateFlow<Consumable<S>?>

    val state: Consumable<S?>?
        get() = stateObservable.value

    fun post(sideEffect: S)

    fun clearHolder()
}