package com.state_manager.side_effects

import com.state_manager.extensions.Consumable
import com.state_manager.extensions.asConsumable
import com.state_manager.logger.Logger
import com.state_manager.logger.logd
import com.state_manager.logger.logv
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class SideEffectHolderImpl<SIDE_EFFECT : SideEffect>(private val logger: Logger) :
    SideEffectHolder<SIDE_EFFECT> {

    private val _observable: MutableStateFlow<Consumable<SIDE_EFFECT>?> = MutableStateFlow(null)

    override val effectObservable: StateFlow<Consumable<SIDE_EFFECT>?>
        get() = _observable

    override fun clearEffectHolder() {
        logger.logv { "Clearing SideEffect Holder" }
    }

    override fun post(sideEffect: SIDE_EFFECT) {
        _observable.value = sideEffect.asConsumable()
        logger.logd { "SideEffect: $sideEffect" }
    }
}