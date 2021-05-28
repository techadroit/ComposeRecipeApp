package com.example.composerecipeapp.core.viewmodel

/**
 * A class which can hold current state as well as handle actions to be performed on it.
 *
 * @param stateHolder The delegate to handle [StateHolder] functions
 * @param stateProcessor The delegate to handle [StateProcessor] functions
 */
abstract class StateStore<S : AppState,E : AppEvent>(
    protected open val stateHolder: StateHolder<S>,
    protected open val stateProcessor: StateProcessor<S,E>
) : StateHolder<S> by stateHolder, StateProcessor<S,E> by stateProcessor {

    /**
     * Clear any resources held by this state store.
     * Implementations should also forward the call to [stateHolder] and [stateProcessor]
     */
    abstract fun clear()
}
