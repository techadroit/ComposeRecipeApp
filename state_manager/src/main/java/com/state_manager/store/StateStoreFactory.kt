package com.state_manager.store

import com.state_manager.events.AppEvent
import com.state_manager.events.EventHolder
import com.state_manager.events.EventHolderImpl
import com.state_manager.logger.Logger
import com.state_manager.reducer.StateProcessor
import com.state_manager.reducer.StateProcessorFactory
import com.state_manager.side_effects.SideEffect
import com.state_manager.side_effects.SideEffectHolder
import com.state_manager.side_effects.SideEffectHolderFactory
import com.state_manager.state.AppState
import com.state_manager.state.StateHolder
import com.state_manager.state.StateHolderFactory
import kotlinx.coroutines.CoroutineScope

/**
 * A factory to create instances of [StateStore]
 */
internal object StateStoreFactory {

    fun <S : AppState, E : AppEvent, SIDE_EFFECTS : SideEffect> create(
        initialState: S,
        logger: Logger,
        coroutineScope: CoroutineScope,
    ): StateStore<S, E, SIDE_EFFECTS> {
        val stateHolder = StateHolderFactory.create(initialState, logger)
        val eventHolder = EventHolderImpl<E>(logger = logger)
        val effectHolder = SideEffectHolderFactory.create<SIDE_EFFECTS>(logger, coroutineScope)
        val stateProcessor =
            StateProcessorFactory.create(
                stateHolder,
                eventHolder,
                effectHolder,
                logger,
                coroutineScope
            )
        return create(stateHolder, stateProcessor, logger, eventHolder, effectHolder)
    }

    fun <S : AppState, E : AppEvent, SIDE_EFFECTS : SideEffect> create(
        stateHolder: StateHolder<S>,
        stateProcessor: StateProcessor<S, E, SIDE_EFFECTS>,
        logger: Logger,
        eventHolder: EventHolder<E>,
        effectHolder: SideEffectHolder<SIDE_EFFECTS>
    ): StateStore<S, E, SIDE_EFFECTS> {
        return StateStoreImpl(stateHolder, stateProcessor, logger, eventHolder, effectHolder)
    }

}
