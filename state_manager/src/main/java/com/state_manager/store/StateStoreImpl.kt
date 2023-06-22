package com.state_manager.store

import com.state_manager.events.AppEvent
import com.state_manager.events.EventHolder
import com.state_manager.logger.Logger
import com.state_manager.logger.logv
import com.state_manager.reducer.StateProcessor
import com.state_manager.side_effects.SideEffect
import com.state_manager.side_effects.SideEffectHolder
import com.state_manager.state.AppState
import com.state_manager.state.StateHolder

/**
 * The default implementation of [StateStore]
 */
internal class StateStoreImpl<S : AppState, E : AppEvent, SIDE_EFFECTS : SideEffect>(
    holder: StateHolder<S>,
    processor: StateProcessor<S, E, SIDE_EFFECTS>,
    private val logger: Logger,
    eHolder: EventHolder<E>,
    override val effectHolder: SideEffectHolder<SIDE_EFFECTS>
) : StateStore<S, E,SIDE_EFFECTS>(holder, processor, eHolder,effectHolder) {

    override fun clear() {
        logger.logv { "Clearing State Store" }
        stateProcessor.clearProcessor()
        stateHolder.clearHolder()
        eventHolder.clearEventHolder()
        effectHolder.clearEffectHolder()
    }
}
