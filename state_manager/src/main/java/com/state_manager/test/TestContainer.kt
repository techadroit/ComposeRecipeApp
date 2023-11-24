package com.state_manager.test

import com.state_manager.events.AppEvent
import com.state_manager.extensions.Consumable
import com.state_manager.extensions.runCreate
import com.state_manager.managers.Manager
import com.state_manager.side_effects.SideEffect
import com.state_manager.state.AppState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlinx.coroutines.withContext
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

typealias testAction = () -> Unit

class TestContainer<S : AppState, E : AppEvent, SIDE_EFFECT : SideEffect>(val manager: Manager<S, E, SIDE_EFFECT>) {

    var dispatcher = UnconfinedTestDispatcher()
    var events: List<E> = emptyList()
    var actions: List<testAction> = emptyList()
    var currentState: S? = null

    fun forEvents(vararg events: E) {
        this.events = events.toList()
    }

    fun forActions(vararg a: testAction) {
        actions = a.toList()
    }

    fun withState(state: S) {
        this.currentState = state
    }

    fun withDispatcher(dispatcher: TestDispatcher) {
        this.dispatcher = dispatcher
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    fun verifyStates(
        dispatcher: CoroutineDispatcher,
        verifier: TestResult.StateResult<S>.() -> Unit,
    ) {
        runTest(dispatcher) {
            currentState?.let {
                manager.setState { it }
                runCurrent()
            }
            advanceUntilIdle()
            val list = mutableListOf<S>()
            backgroundScope.launch(dispatcher) {
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

    @OptIn(ExperimentalCoroutinesApi::class)
    fun verify(
        verifier: TestResult.StateResult<S>.() -> Unit,
    ) {
        runTest {
            // use this dispatcher to run events and actions for sequential execution
            val dispatcher = StandardTestDispatcher(testScheduler)
            // use this dispatcher to run actions immediately
            val eagerDispatcher = UnconfinedTestDispatcher(testScheduler)
            Dispatchers.setMain(dispatcher)
            try {
                // execute all the pending events or actions
                manager.runCreate(this)
                // initialize default state to the state manager
                initializeDefaultState(dispatcher)
                val list = mutableListOf<S>()
                // background scope makes sure it is cancelled after test completion
                backgroundScope.launch(eagerDispatcher) {
                    manager.stateEmitter.toList(list)
                }
                // run all actions and events sequentially
                runActions(dispatcher)
                verifier(TestResult.StateResult(list))
            } finally {
                Dispatchers.resetMain()
            }
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    fun verifyEffects(
        verifier: TestResult.SideEffectsResult<SIDE_EFFECT>.() -> Unit
    ) {
        runTest {
            // use this dispatcher to run events and actions for sequential execution
            val dispatcher = StandardTestDispatcher(testScheduler)
            // use this dispatcher to run actions immediately
            val eagerDispatcher = UnconfinedTestDispatcher(testScheduler)
            Dispatchers.setMain(dispatcher)
            try {
                // execute all the pending events or actions
                manager.runCreate(this)
                // initialize default state to the state manager
                initializeDefaultState(dispatcher)
                val list = mutableListOf<SIDE_EFFECT?>()
                // background scope makes sure it is cancelled after test completion
                backgroundScope.launch(eagerDispatcher) {
                    manager.onSideEffect().toList(list)
                }
                // run all actions and events sequentially
                runActions(dispatcher)
                verifier(TestResult.SideEffectsResult(list.mapNotNull { it }))
            } finally {
                Dispatchers.resetMain()
            }
        }
    }

    suspend fun TestScope.runActions(dispatcher: CoroutineDispatcher) {
        withContext(dispatcher) {
            events.forEach {
                manager.dispatch(it)
                runCurrent()
            }
            actions.forEach {
                it.invoke()
                runCurrent()
            }
            advanceUntilIdle()
        }
    }

    suspend fun TestScope.initializeDefaultState(dispatcher: CoroutineDispatcher) {
        withContext(dispatcher) {
            (currentState ?: manager.initialState).let {
                manager.setState { it }
                runCurrent()
            }
            advanceUntilIdle()
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