package com.archerviewmodel.store

import com.archerviewmodel.events.ArcherEvent
import com.archerviewmodel.events.EventHolder
import com.archerviewmodel.reducer.StateProcessor
import com.archerviewmodel.state.ArcherState
import com.archerviewmodel.state.StateHolder

/**
 * A class which can hold current state as well as handle actions to be performed on it.
 *
 * @param stateHolder The delegate to handle [StateHolder] functions
 * @param stateProcessor The delegate to handle [StateProcessor] functions
 */
abstract class StateStore<S : ArcherState, E : ArcherEvent>(
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
