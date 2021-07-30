package com.archerviewmodel.reducer

import com.archerviewmodel.events.ArcherEvent
import com.archerviewmodel.events.EventHolder
import com.archerviewmodel.state.ArcherState
import com.archerviewmodel.state.StateHolder
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
    fun <S : ArcherState, E : ArcherEvent> create(
        stateHolder: StateHolder<S>,
        eventHolder: EventHolder<E>,
        logger: Logger,
        coroutineContext: CoroutineContext
    ): StateProcessor<S, E> {
        return SelectBasedStateProcessor(
            shouldStartImmediately = true,
            eventHolder = eventHolder,
            stateHolder = stateHolder,
            logger = logger,
            coroutineContext = coroutineContext
        )
    }
}
