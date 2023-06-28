package com.state_manager.viewmodel_test

import com.state_manager.BaseUnitTest
import com.state_manager.TestStateManagerScope
import com.state_manager.events.AppEvent
import com.state_manager.extensions.runCreate
import com.state_manager.extensions.verifyState
import com.state_manager.extensions.verifyState1
import com.state_manager.managers.Manager
import com.state_manager.side_effects.SideEffect
import com.state_manager.state.AppState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
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
        viewModel = TestViewModel(initialTestState = initialState, testStateManagerScope)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun checkInitialState() {
//        runTest {
//            val list = mutableListOf<TestState>()
//            backgroundScope.launch {
//                viewModel.stateEmitter.toList(list)
//            }
//            println(list)
//            assert(list.isNotEmpty())
//        }
        runBlocking {
            val list = mutableListOf<TestState>()

            val job = launch {
                viewModel.stateEmitter.toList(list)
            }
            viewModel.dispatch(IncrementCountEvent(1))
            println(list)
            job.cancel()
            assert(list.isNotEmpty())
        }
    }

//    @ExperimentalCoroutinesApi
//    @Test
//    fun checkListOfStates() {
//        runTest {
//            viewModel.verifyState(
//                IncrementCountEvent(1),
//                IncrementCountEvent(1)
//            ) {
//                print(it)
//                assert(it.isNotEmpty())
//            }
//        }
//    }

    @ExperimentalCoroutinesApi
    @Test
    fun checkListOfStates2() {
            viewModel.verifyState(
                IncrementCountEvent(5),
                IncrementCountEvent(10),
                IncrementCountEvent(10)
            ) {
                print(it)
                assert(it == listOf(TestState(5), TestState(15), TestState(25)))
            }
        }
//    @ExperimentalCoroutinesApi
//    @Test
//    fun checkListOfStates3() {
//        runTest {
//            viewModel.verifyState(
//                DecrementCountEvent(5),
//                IncrementCountEvent(10)
//            ) {
//                print(it)
//                assert(it == listOf(TestState(-5), TestState(5)))
//            }
//        }
//    }

//    @Test
//    fun clearTest() {
//        viewModel.clear()
//        assert(testStateManagerScope.isCleared())
//    }
}

