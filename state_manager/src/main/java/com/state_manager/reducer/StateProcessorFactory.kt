package com.state_manager.reducer

import com.state_manager.events.AppEvent
import com.state_manager.events.EventHolder
import com.state_manager.state.AppState
import com.state_manager.state.StateHolder
import com.state_manager.logger.Logger
import kotlinx.coroutines.CoroutineScope

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
    fun <S : AppState, E : AppEvent> create(
        stateHolder: StateHolder<S>,
        eventHolder: EventHolder<E>,
        logger: Logger,
        coroutineScope: CoroutineScope
    ): StateProcessor<S, E> {
        return SelectBasedStateProcessor(
            shouldStartImmediately = true,
            eventHolder = eventHolder,
            stateHolder = stateHolder,
            logger = logger,
            processorScope = coroutineScope
        )
    }
}
