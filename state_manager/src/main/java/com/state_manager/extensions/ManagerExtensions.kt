package com.state_manager.extensions

import com.state_manager.events.AppEvent
import com.state_manager.managers.Manager
import com.state_manager.side_effects.SideEffect
import com.state_manager.state.AppState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.withContext

suspend fun <S : AppState, E : AppEvent, SIDE_EFFECT : SideEffect> Manager<S, E, SIDE_EFFECT>.testCurrentState(
    verifyer: (S) -> Unit
) {
    verifyer(currentState)
}

fun <S : AppState> List<S>.noInitialState() {

}

suspend fun <S : AppState, E : AppEvent, SIDE_EFFECT : SideEffect> Manager<S, E, SIDE_EFFECT>.runCreate(
    scope: CoroutineScope
) {
    stateStore.drain(scope)
    setState {
        initialState
    }
}

@OptIn(ExperimentalCoroutinesApi::class)
fun <S : AppState, E : AppEvent, SIDE_EFFECT : SideEffect> Manager<S, E, SIDE_EFFECT>.verifyState1(
    scope: TestScope,
    vararg events: E,
    verifier: (List<S>) -> Unit
) {

    runTest {
        runCreate(backgroundScope)

        val list = mutableListOf<S>()
        val job = scope.launch {
            stateEmitter.testAll(scope)
        }

        events.forEach {
            dispatch(it)
            runCurrent()
        }
        advanceUntilIdle()
        // droping initial state
        verifier(list.drop(0))
        job.cancel()
    }
}

@OptIn(ExperimentalCoroutinesApi::class)
fun <S : AppState, E : AppEvent, SIDE_EFFECT : SideEffect> Manager<S, E, SIDE_EFFECT>.verifyState(
    vararg events: E,
    verifier: (MutableList<S>) -> Unit
) {

    runTest(UnconfinedTestDispatcher()) {
        withContext(Dispatchers.Default.limitedParallelism(1)) {
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
            // droping initial state
            verifier(list)
        }
    }
}