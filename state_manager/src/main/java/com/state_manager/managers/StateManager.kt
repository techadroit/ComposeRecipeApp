package com.state_manager.managers

import com.state_manager.events.EmptyEvent
import com.state_manager.scopes.StateManagerCoroutineScope
import com.state_manager.scopes.StateManagerCoroutineScopeImpl
import com.state_manager.state.ArcherState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

/**
 * ViewModel Which doesn't enforce event driven Structure
 */
open class StateManager<T : ArcherState>(initialState: T,
                                         coroutineScope: StateManagerCoroutineScope
                                         = StateManagerCoroutineScopeImpl(Dispatchers.Default + SupervisorJob())
) : StateEventManager<T, EmptyEvent>(initialState = initialState) {
    override fun onEvent(event: EmptyEvent, state: T) {
    }
}
