package com.state_manager.test

import com.state_manager.events.AppEvent
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
    var states: List<S> = emptyList()
    var events: List<E> = emptyList()
    val effects: List<SIDE_EFFECT> = emptyList()

    fun forEvents(vararg events: E) {
        this.events = events.toList()
    }
//
//    fun expect(vararg states: S) {
//        this.states = states.toList()
//    }

    fun verify(
        verifier: TestResult.StateResult<S>.() -> Unit
    ) {
        runTest(dispatcher) {
            manager.runCreate(backgroundScope)

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
}

sealed class TestResult{
    data class StateResult<S: AppState>(val emittedStates:List<S>) : TestResult()
}

@OptIn(ExperimentalContracts::class)
public inline fun <T> T.test(block: T.() -> Unit): T {
    contract {
        callsInPlace(block, InvocationKind.EXACTLY_ONCE)
    }
    block()
    return this
}