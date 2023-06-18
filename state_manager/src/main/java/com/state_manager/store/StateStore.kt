package com.state_manager.store

import com.state_manager.events.AppEvent
import com.state_manager.events.EventHolder
import com.state_manager.reducer.StateProcessor
import com.state_manager.state.ArcherState
import com.state_manager.state.StateHolder

/**
 * A class which can hold current state as well as handle actions to be performed on it.
 *
 * @param stateHolder The delegate to handle [StateHolder] functions
 * @param stateProcessor The delegate to handle [StateProcessor] functions
 */
abstract class StateStore<S : ArcherState, E : AppEvent>(
    protected open val stateHolder: StateHolder<S>,
    protected open val stateProcessor: StateProcessor<S, E>,
    protected open val eventHolder: EventHolder<E>
) : StateHolder<S> by stateHolder,
    EventHolder<E> by eventHolder,
    StateProcessor<S, E> by stateProcessor {

    /**
     * Clear any resources held by this state store.
     * Implementations should also forward the call to [stateHolder] and [stateProcessor]
     */
    abstract fun clear()
}