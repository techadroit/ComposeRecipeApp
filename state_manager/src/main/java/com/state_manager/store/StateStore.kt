package com.state_manager.store

import com.state_manager.events.AppEvent
import com.state_manager.events.EventHolder
import com.state_manager.reducer.StateProcessor
import com.state_manager.side_effects.SideEffect
import com.state_manager.side_effects.SideEffectHolder
import com.state_manager.state.AppState
import com.state_manager.state.StateHolder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.isActive

/**
 * A class which can hold current state as well as handle actions to be performed on it.
 *
 * @param stateHolder The delegate to handle [StateHolder] functions
 * @param stateProcessor The delegate to handle [StateProcessor] functions
 */
abstract class StateStore<S : AppState, E : AppEvent, SIDE_EFFECT : SideEffect>(
    protected open val stateHolder: StateHolder<S>,
    protected open val stateProcessor: StateProcessor<S, E, SIDE_EFFECT>,
    protected open val eventHolder: EventHolder<E>,
    protected open val effectHolder: SideEffectHolder<SIDE_EFFECT>
) : StateHolder<S> by stateHolder,
    EventHolder<E> by eventHolder,
    SideEffectHolder<SIDE_EFFECT> by effectHolder,
    StateProcessor<S, E, SIDE_EFFECT> by stateProcessor {

    /**
     * Clear any resources held by this state store.
     * Implementations should also forward the call to [stateHolder] and [stateProcessor]
     */
    abstract fun clear()

    /**
     * empty all pending reducer and action in the queue
     */
    suspend fun emptyProcessor(scope: CoroutineScope) {
        stateProcessor.drain(scope)
    }
}
