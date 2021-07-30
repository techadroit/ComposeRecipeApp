package com.archerviewmodel.store

import com.archerviewmodel.events.ArcherEvent
import com.archerviewmodel.events.EventHolder
import com.archerviewmodel.events.EventHolderImpl
import com.archerviewmodel.reducer.StateProcessor
import com.archerviewmodel.reducer.StateProcessorFactory
import com.archerviewmodel.state.ArcherState
import com.archerviewmodel.state.StateHolder
import com.archerviewmodel.state.StateHolderFactory
import com.example.composerecipeapp.core.logger.Logger
import kotlin.coroutines.CoroutineContext

/**
 * A factory to create instances of [StateStore]
 */
internal object StateStoreFactory {

    fun <S : ArcherState, E : ArcherEvent> create(
        initialState: S,
        logger: Logger,
        coroutineContext: CoroutineContext,
    ): StateStore<S, E> {
        val stateHolder = StateHolderFactory.create(initialState, logger)
        val eventHolder = EventHolderImpl<E>(logger = logger)
        val stateProcessor =
            StateProcessorFactory.create(stateHolder, eventHolder, logger, coroutineContext)
        return create(stateHolder, stateProcessor, logger, eventHolder)
    }

    fun <S : ArcherState, E : ArcherEvent> create(
        stateHolder: StateHolder<S>,
        stateProcessor: StateProcessor<S, E>,
        logger: Logger,
        eventHolder: EventHolder<E>
    ): StateStore<S, E> {
        return StateStoreImpl(stateHolder, stateProcessor, logger, eventHolder)
    }

//    fun <S : ArcherState> create(
//        initialState: S,
//        logger: Logger,
//        stateHolderFactory: StateHolderFactory,
//        stateProcessorFactory: StateProcessorFactory,
//        coroutineContext: CoroutineContext
//    ): StateStore<S> {
//        val holder = stateHolderFactory.create(initialState, logger)
//        val processor = stateProcessorFactory.create(holder, logger, coroutineContext)
//        return create(holder, processor, logger)
//    }
}
