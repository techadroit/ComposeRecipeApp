package com.state_manager.handler

import com.state_manager.extensions.Consumable
import com.state_manager.logger.Logger
import com.state_manager.side_effects.SideEffect
import com.state_manager.side_effects.SideEffectHolderFactory
import kotlinx.coroutines.flow.StateFlow

class SideEffectHandlerDelegation<S : SideEffect>(logger: Logger) : SideEffectHandler<S> {

    private val sideEffectHolder = SideEffectHolderFactory.create<S>(logger)
    override fun postSideEffect(sideEffect: S) = sideEffectHolder.post(sideEffect)

    override fun onSideEffect(): StateFlow<Consumable<S?>?>  = sideEffectHolder.effectObservable
}