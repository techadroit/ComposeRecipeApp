package com.example.composerecipeapp.core.viewmodel

import com.example.composerecipeapp.core.logger.Logger
import kotlin.coroutines.CoroutineContext
import kotlin.math.log

/**
 * A factory to create instances of [StateStore]
 */
internal object StateStoreFactory {

    fun <S : AppState,E : AppEvent> create(
        initialState: S,
        logger: Logger,
        coroutineContext: CoroutineContext,
        event: E?
    ): StateStore<S,E> {
        val stateHolder = StateHolderFactory.create(initialState, logger)
        val eventHolder = EventHolderImpl<E>(logger = logger)
        val stateProcessor = StateProcessorFactory.create(stateHolder,eventHolder, logger, coroutineContext)
        return create(stateHolder, stateProcessor, logger,eventHolder)
    }

    fun <S : AppState, E : AppEvent> create(
        stateHolder: StateHolder<S>,
        stateProcessor: StateProcessor<S,E>,
        logger: Logger,
        eventHolder: EventHolder<E>
    ): StateStore<S,E> {
        return StateStoreImpl(stateHolder, stateProcessor, logger)
    }

//    fun <S : AppState> create(
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
