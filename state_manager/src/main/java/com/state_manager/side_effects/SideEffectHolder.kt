package com.state_manager.side_effects

import kotlinx.coroutines.flow.SharedFlow

interface SideEffectHolder<SIDE_EFFECT: SideEffect> {

    val effectObservable: SharedFlow<SIDE_EFFECT?>

    fun post(sideEffect: SIDE_EFFECT)

    fun clearEffectHolder()
}