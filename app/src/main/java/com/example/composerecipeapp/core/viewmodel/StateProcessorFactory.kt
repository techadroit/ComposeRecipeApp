package com.example.composerecipeapp.core.viewmodel

import com.example.composerecipeapp.core.logger.Logger
import kotlin.coroutines.CoroutineContext

/**
 * A Factory which produces instances of [StateProcessor]
 */
internal object StateProcessorFactory {

    /**
     * Create and return an instance of [StateProcessor]
     *
     * @param S The state type to be associated with this processor
     * @param logger A logger to be supplied to the state processor
     * @param coroutineContext The context of execution of the state processor
     *
     * @return A class implementing StateProcessor
     */
    fun <S : AppState,E : AppEvent> create(
        stateHolder: StateHolder<S>,
        eventHolder: EventHolder<E>,
        logger: Logger,
        coroutineContext: CoroutineContext
    ): StateProcessor<S,E> {
        return SelectBasedStateProcessor(
            shouldStartImmediately = true,
            eventHolder = eventHolder,
            stateHolder = stateHolder,
            logger = logger,
            coroutineContext = coroutineContext
        )
    }
}
