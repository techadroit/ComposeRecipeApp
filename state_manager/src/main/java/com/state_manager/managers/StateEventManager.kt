package com.state_manager.managers

import com.state_manager.events.AppEvent
import com.state_manager.scopes.StateManagerCoroutineScope
import com.state_manager.scopes.StateManagerCoroutineScopeImpl
import com.state_manager.state.AppState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

abstract class StateEventManager<S : AppState, E : AppEvent>(
    initialState: S,
    coroutineScope: StateManagerCoroutineScope = StateManagerCoroutineScopeImpl(Dispatchers.Default + SupervisorJob())
) : Manager<S, E>(initialState, coroutineScope)
