package com.state_manager.events

import kotlinx.coroutines.flow.StateFlow

interface EventHolder<E : AppEvent?> {

    val eventObservable: StateFlow<E?>

    /**
     * A convenient way to access the current event value in the [stateObservable]
     */
    val event: E?
        get() = eventObservable.value

    /**
     * Updates the state contained in this state holder
     */
    fun addEvent(newEvent: E)

    /**
     * This method is expected to be called when this state holder is no longer being used
     */
    fun clearEventHolder()
}
