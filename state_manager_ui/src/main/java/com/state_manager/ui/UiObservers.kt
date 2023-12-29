package com.state_manager.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.state_manager.events.AppEvent
import com.state_manager.managers.Manager
import com.state_manager.managers.StateEventManager
import com.state_manager.side_effects.SideEffect
import com.state_manager.state.AppState

@Composable
fun <S : AppState, E : AppEvent> StateEventManager<S, E>.observeSideEffects(content: @Composable (SideEffect) -> Unit) {
    onSideEffect().collectAsStateWithLifecycle(initialValue = null).value?.let {
        content(it)
    }
}

@Composable
fun <S : AppState, E : AppEvent> Manager<S, E, SideEffect>.observeState(content: @Composable (state: S) -> Unit) {
    val state = this.stateEmitter.collectAsState().value
    content(state)
}

@Composable
fun <S : AppState, E : AppEvent> Manager<S, E, SideEffect>.getState() =
    this.stateEmitter.collectAsState().value