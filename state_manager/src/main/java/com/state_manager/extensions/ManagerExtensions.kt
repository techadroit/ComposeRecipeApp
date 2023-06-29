package com.state_manager.extensions

import com.state_manager.events.AppEvent
import com.state_manager.managers.Manager
import com.state_manager.side_effects.SideEffect
import com.state_manager.state.AppState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.job
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.withContext

@OptIn(ExperimentalCoroutinesApi::class)
fun <S : AppState, E : AppEvent, SIDE_EFFECT : SideEffect> Manager<S, E, SIDE_EFFECT>.verifySideEffects(
    vararg events: E,
    verifyer: (List<SIDE_EFFECT>) -> Unit
) {
    runTest(UnconfinedTestDispatcher()) {
        val list = mutableListOf<Consumable<SIDE_EFFECT?>?>()
        backgroundScope.launch {
            onSideEffect().toList(list)
        }
        events.onEach {
            dispatch(it)
            runCurrent()
        }
        val newState = list.map { it?.consume() }.filterNotNull()
        verifyer(newState)
    }
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
            verifier(list)
        }
    }
}