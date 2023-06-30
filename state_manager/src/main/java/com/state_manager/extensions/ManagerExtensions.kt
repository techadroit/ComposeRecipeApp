package com.state_manager.extensions

import com.state_manager.events.AppEvent
import com.state_manager.managers.Manager
import com.state_manager.side_effects.SideEffect
import com.state_manager.state.AppState
import com.state_manager.test.TestContainer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest

suspend fun <S : AppState, E : AppEvent, SIDE_EFFECT : SideEffect> Manager<S, E, SIDE_EFFECT>.testCurrentState(
    verifyer: (S) -> Unit
) {
    verifyer(currentState)
}

suspend fun <S : AppState, E : AppEvent, SIDE_EFFECT : SideEffect> Manager<S, E, SIDE_EFFECT>.runCreate(
    scope: CoroutineScope
) {
    stateStore.drain(scope)
    setState {
        initialState
    }
}

fun <S : AppState, E : AppEvent, SIDE_EFFECT : SideEffect> Manager<S, E, SIDE_EFFECT>.createTestContainer(): TestContainer<S, E, SIDE_EFFECT> {
    return TestContainer(this)
}

@OptIn(ExperimentalCoroutinesApi::class)
fun <S : AppState, E : AppEvent, SIDE_EFFECT : SideEffect> Manager<S, E, SIDE_EFFECT>.verifyState(
    dispatcher: TestDispatcher = UnconfinedTestDispatcher(),
    vararg events: E,
    verifier: (MutableList<S>) -> Unit
) {
    runTest(dispatcher) {
        runCreate(backgroundScope)

        val list = mutableListOf<S>()
        backgroundScope.launch {
            stateEmitter.toList(list)
        }
        events.forEach {
            dispatch(it)
            runCurrent()
        }
        advanceUntilIdle()
        verifier(list)
    }
}