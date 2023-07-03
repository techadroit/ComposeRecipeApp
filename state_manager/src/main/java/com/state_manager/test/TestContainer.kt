package com.state_manager.test

import com.state_manager.events.AppEvent
import com.state_manager.extensions.Consumable
import com.state_manager.extensions.runCreate
import com.state_manager.managers.Manager
import com.state_manager.side_effects.SideEffect
import com.state_manager.state.AppState
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

class TestContainer<S : AppState, E : AppEvent, SIDE_EFFECT : SideEffect>(val manager: Manager<S, E, SIDE_EFFECT>) {

    val dispatcher = manager.coroutineScope.dispatcher
    var events: List<E> = emptyList()
    var initialState = manager.initialState

    fun forEvents(vararg events: E) {
        this.events = events.toList()
    }

    fun withState(state: S) {
        this.initialState = state
    }

    fun verify(
        verifier: TestResult.StateResult<S>.() -> Unit
    ) {
        runTest(dispatcher) {
            manager.runCreate(initialState, backgroundScope)

            val list = mutableListOf<S>()
            backgroundScope.launch {
                manager.stateEmitter.toList(list)
            }
            events.forEach {
                manager.dispatch(it)
                runCurrent()
            }
            advanceUntilIdle()
            verifier(TestResult.StateResult(list))
        }
    }

    fun verifyEffects(
        verifier: TestResult.SideEffectsResult<SIDE_EFFECT>.() -> Unit
    ) {
        runTest(dispatcher) {
            manager.runCreate(initialState, backgroundScope)

            val list = mutableListOf<Consumable<SIDE_EFFECT?>?>()
            backgroundScope.launch {
                manager.onSideEffect().toList(list)
            }
            events.forEach {
                manager.dispatch(it)
                runCurrent()
            }
            advanceUntilIdle()
            verifier(TestResult.SideEffectsResult(list.mapNotNull { it?.consume() }))
        }
    }
}

sealed class TestResult {
    data class StateResult<S : AppState>(val emittedStates: List<S>) : TestResult()
    data class SideEffectsResult<SIDE_EFFECT : SideEffect>(val emittedEffects: List<SIDE_EFFECT>) :
        TestResult()
}

@OptIn(ExperimentalContracts::class)
public inline fun <T> T.test(block: T.() -> Unit): T {
    contract {
        callsInPlace(block, InvocationKind.EXACTLY_ONCE)
    }
    block()
    return this
}