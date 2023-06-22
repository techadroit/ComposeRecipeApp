package com.state_manager.reducer

import com.state_manager.events.AppEvent
import com.state_manager.events.EventHolder
import com.state_manager.state.AppState
import com.state_manager.state.StateHolder
import com.state_manager.logger.Logger
import com.state_manager.side_effects.SideEffect
import com.state_manager.side_effects.SideEffectHolder
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
    fun <S : AppState, E : AppEvent,SIDE_EFFECT : SideEffect> create(
        stateHolder: StateHolder<S>,
        eventHolder: EventHolder<E>,
        effectHolder: SideEffectHolder<SIDE_EFFECT>,
        logger: Logger,
        coroutineScope: CoroutineScope
    ): StateProcessor<S, E,SIDE_EFFECT> {
        return SingleChannelProcessor(
            shouldStartImmediately = true,
            eventHolder = eventHolder,
            stateHolder = stateHolder,
            sideEffectHolder = effectHolder,
            logger = logger,
            processorScope = coroutineScope
        )
    }
}
