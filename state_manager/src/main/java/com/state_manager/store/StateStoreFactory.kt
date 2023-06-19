package com.state_manager.store

import com.state_manager.events.AppEvent
import com.state_manager.events.EventHolder
import com.state_manager.events.EventHolderImpl
import com.state_manager.reducer.StateProcessor
import com.state_manager.reducer.StateProcessorFactory
import com.state_manager.state.AppState
import com.state_manager.state.StateHolder
import com.state_manager.state.StateHolderFactory
import com.state_manager.logger.Logger
import kotlinx.coroutines.CoroutineScope

/**
 * A factory to create instances of [StateStore]
 */
internal object StateStoreFactory {

    fun <S : AppState, E : AppEvent> create(
        initialState: S,
        logger: Logger,
        coroutineScope: CoroutineScope,
    ): StateStore<S, E> {
        val stateHolder = StateHolderFactory.create(initialState, logger)
        val eventHolder = EventHolderImpl<E>(logger = logger)
        val stateProcessor =
            StateProcessorFactory.create(stateHolder, eventHolder, logger, coroutineScope)
        return create(stateHolder, stateProcessor, logger, eventHolder)
    }

    fun <S : AppState, E : AppEvent> create(
        stateHolder: StateHolder<S>,
        stateProcessor: StateProcessor<S, E>,
        logger: Logger,
        eventHolder: EventHolder<E>
    ): StateStore<S, E> {
        return StateStoreImpl(stateHolder, stateProcessor, logger, eventHolder)
    }

}
