package com.state_manager.managers

import com.state_manager.events.EmptyEvent
import com.state_manager.scopes.StateManagerCoroutineScope
import com.state_manager.scopes.StateManagerCoroutineScopeImpl
import com.state_manager.side_effects.SideEffect
import com.state_manager.state.AppState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

/**
 * ViewModel Which doesn't enforce event driven Structure
 */
open class StateManager<T : AppState>(initialState: T,
                                      coroutineScope: StateManagerCoroutineScope
                                         = StateManagerCoroutineScopeImpl(Dispatchers.Default)
) : Manager<T, EmptyEvent,SideEffect>(initialState = initialState) {

    /**
     * Make onEvent final to avoid override of this method in base class.
     * Use [StateEventManager] to override this method and follow event driven structure
     */
    final override fun onEvent(event: EmptyEvent, state: T) {
    }
}
