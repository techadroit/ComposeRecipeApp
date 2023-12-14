package com.state_manager.extension

import com.state_manager.events.AppEvent
import com.state_manager.managers.Manager
import com.state_manager.side_effects.SideEffect
import com.state_manager.state.AppState
import kotlinx.coroutines.CoroutineScope
import com.state_manager.test.TestContainer

suspend fun <S : AppState, E : AppEvent, SIDE_EFFECT : SideEffect> Manager<S, E, SIDE_EFFECT>.runCreate(
    scope: CoroutineScope
) {
    stateStore.drain(scope)
}

fun <S : AppState, E : AppEvent, SIDE_EFFECT : SideEffect> Manager<S, E, SIDE_EFFECT>.createTestContainer(): TestContainer<S, E, SIDE_EFFECT> {
    return TestContainer(this)
}