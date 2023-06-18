package com.state_manager.managers

import com.state_manager.events.AppEvent
import com.state_manager.scopes.StateManagerCoroutineScope
import com.state_manager.scopes.StateManagerCoroutineScopeImpl
import com.state_manager.state.ArcherState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlin.coroutines.CoroutineContext

abstract class StateEventManager<S : ArcherState, E : AppEvent>(
    initialState: S,
    coroutineScope: StateManagerCoroutineScope = StateManagerCoroutineScopeImpl(Dispatchers.Default + SupervisorJob())
) : Manager<S, E>(initialState, coroutineScope)
