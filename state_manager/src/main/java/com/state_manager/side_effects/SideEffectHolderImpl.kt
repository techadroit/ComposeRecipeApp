package com.state_manager.side_effects

import com.state_manager.logger.Logger
import com.state_manager.logger.logv
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class SideEffectHolderImpl<S:SideEffect>(private val logger: Logger) : SideEffectHolder<S> {

    private val _observable: MutableStateFlow<S?> = MutableStateFlow(null)

    override val stateObservable: StateFlow<S?>
        get() = _observable

    override fun clearHolder() {
        logger.logv { "Clearing State Holder" }
    }

    override fun post(sideEffect: S) {
        _observable.value = sideEffect
    }
}