package com.state_manager.extensions

import com.state_manager.events.AppEvent
import com.state_manager.logger.androidLogger
import com.state_manager.managers.Manager
import com.state_manager.side_effects.SideEffect
import com.state_manager.state.AppState
import com.state_manager.store.StateStoreFactory
import kotlinx.coroutines.CoroutineScope

suspend fun <S : AppState, E : AppEvent, SIDE_EFFECT : SideEffect> Manager<S, E, SIDE_EFFECT>.testCurrentState(
    verifyer: (S) -> Unit
) {
    verifyer(currentState)
}

suspend fun <S : AppState, E : AppEvent, SIDE_EFFECT : SideEffect> Manager<S, E, SIDE_EFFECT>.runCreate(scope: CoroutineScope){
    stateStore.drain(scope)
    setState {
        initialState
    }
}

