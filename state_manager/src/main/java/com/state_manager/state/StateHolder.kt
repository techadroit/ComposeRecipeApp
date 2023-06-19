package com.state_manager.state

import kotlinx.coroutines.flow.StateFlow

interface StateHolder<S : AppState> {

    /**
     * A [StateFlow] to expose the state as an observable entity.
     * This flow is conflated, so only the latest state value is present in it
     *
     * To be notified of every state update, use the [kotlinx.coroutines.flow.buffer] operator.
     */
    val stateObservable: StateFlow<S>

    /**
     * A convenient way to access the current state value in the [stateObservable]
     */
    val state: S
        get() = stateObservable.value

    /**
     * Updates the state contained in this state holder
     */
    fun updateState(newState: S)

    /**
     * This method is expected to be called when this state holder is no longer being used
     */
    fun clearHolder()
}
