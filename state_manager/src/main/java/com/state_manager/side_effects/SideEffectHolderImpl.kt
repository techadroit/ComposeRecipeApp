package com.state_manager.side_effects

import com.state_manager.extensions.Consumable
import com.state_manager.extensions.asConsumable
import com.state_manager.logger.Logger
import com.state_manager.logger.logd
import com.state_manager.logger.logv
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class SideEffectHolderImpl<S:SideEffect>(private val logger: Logger) : SideEffectHolder<S> {

    private val _observable: MutableStateFlow<Consumable<S>?> = MutableStateFlow(null)

    override val stateObservable: StateFlow<Consumable<S>?>
        get() = _observable

    override fun clearHolder() {
        logger.logv { "Clearing SideEffect Holder" }
    }

    override fun post(sideEffect: S) {
        _observable.value = sideEffect.asConsumable()
         logger.logd { "SideEffect: $sideEffect" }
    }
}