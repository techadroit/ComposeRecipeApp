package com.example.composerecipeapp.core.viewmodel

import com.example.composerecipeapp.core.logger.Logger
import com.example.composerecipeapp.core.logger.logv

/**
 * The default implementation of [StateStore]
 */
internal class StateStoreImpl<S : AppState,E: AppEvent> (
    holder: StateHolder<S>,
    processor: StateProcessor<S,E>,
    private val logger: Logger,
    eHolder: EventHolder<E>
) : StateStore<S,E>(holder, processor,eHolder) {

    override fun clear() {
        logger.logv { "Clearing State Store" }
        stateProcessor.clearProcessor()
        stateHolder.clearHolder()
        eventHolder.clearEventHolder()
    }
}
