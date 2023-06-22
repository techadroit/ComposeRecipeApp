package com.state_manager.side_effects

import com.state_manager.extensions.Consumable
import kotlinx.coroutines.flow.StateFlow

interface SideEffectHolder<SIDE_EFFECT: SideEffect> {

    val effectObservable: StateFlow<Consumable<SIDE_EFFECT>?>

    val effects: Consumable<SIDE_EFFECT?>?
        get() = effectObservable.value

    fun post(sideEffect: SIDE_EFFECT)

    fun clearEffectHolder()
}