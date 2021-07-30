package com.archerviewmodel.store

import com.archerviewmodel.events.ArcherEvent
import com.archerviewmodel.events.EventHolder
import com.archerviewmodel.reducer.StateProcessor
import com.archerviewmodel.state.ArcherState
import com.archerviewmodel.state.StateHolder
import com.example.composerecipeapp.core.logger.Logger
import com.example.composerecipeapp.core.logger.logv

/**
 * The default implementation of [StateStore]
 */
internal class StateStoreImpl<S : ArcherState, E : ArcherEvent> (
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
