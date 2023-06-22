package com.state_manager.viewmodel_test

import com.state_manager.BaseUnitTest
import com.state_manager.TestStateManagerScope
import com.state_manager.events.AppEvent
import com.state_manager.managers.Manager
import com.state_manager.side_effects.SideEffect
import com.state_manager.state.AppState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

internal class StateEventManagerTest : BaseUnitTest() {
    lateinit var viewModel: TestViewModel
    private val initialState = TestState()
    private val testStateManagerScope = TestStateManagerScope()

    @ExperimentalCoroutinesApi
    @Before
    fun setUp() {
        viewModel = TestViewModel(initialState = initialState, testStateManagerScope)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun checkInitialState() {
        runTest {
            assert(viewModel.currentState == TestState())
        }
    }

    @ExperimentalCoroutinesApi
    @Test
    fun checkListOfStates() {
        runTest {
            viewModel.verifyState(
                IncrementCountEvent(1),
                IncrementCountEvent(1)
            ) {
                print(it)
                assert(it.isNotEmpty())
            }
        }
    }

    @ExperimentalCoroutinesApi
    @Test
    fun checkListOfStates2() {
        runTest {
            viewModel.verifyState(
                IncrementCountEvent(1),
                IncrementCountEvent(2)
            ) {
                print(it)
                assert(it == listOf(TestState(1), TestState(3)))
            }
        }
    }

    @Test
    fun clearTest() {
        viewModel.clear()
        assert(testStateManagerScope.isCleared())
    }
}

@OptIn(ExperimentalCoroutinesApi::class)
suspend fun <S : AppState, E : AppEvent, SIDE_EFFECT : SideEffect> Manager<S, E, SIDE_EFFECT>.verifyState(
    vararg events: E,
    verifyer: (List<S>) -> Unit
) {
    runTest(UnconfinedTestDispatcher()) {
        val list = mutableListOf<S>()
        backgroundScope.launch {
            stateEmitter.toList(list)
        }
        events.forEach {
            dispatch(it)
        }
        // droping initial state
        verifyer(list.drop(1))
    }
}
