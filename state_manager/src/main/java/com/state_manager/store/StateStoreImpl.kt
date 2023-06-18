package com.state_manager.store

import com.state_manager.events.AppEvent
import com.state_manager.events.EventHolder
import com.state_manager.reducer.StateProcessor
import com.state_manager.state.ArcherState
import com.state_manager.state.StateHolder
import com.state_manager.logger.Logger
import com.state_manager.logger.logv

/**
 * The default implementation of [StateStore]
 */
internal class StateStoreImpl<S : ArcherState, E : AppEvent> (
    holder: StateHolder<S>,
    processor: StateProcessor<S, E>,
    private val logger: Logger,
    eHolder: EventHolder<E>
) : StateStore<S, E>(holder, processor, eHolder) {

    override fun clear() {
        logger.logv { "Clearing State Store" }
        stateProcessor.clearProcessor()
        stateHolder.clearHolder()
        eventHolder.clearEventHolder()
    }
}
