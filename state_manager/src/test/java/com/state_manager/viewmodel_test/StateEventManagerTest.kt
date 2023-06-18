package com.state_manager.viewmodel_test

import com.state_manager.BaseUnitTest
import com.state_manager.TestStateManagerScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.launch
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
    fun checkLatestEventReceived() {
        runTest {
            val job = launch {
                viewModel.dispatch(DecrementCountEvent(1))
                viewModel.dispatch(IncrementCountEvent(1))
                val event = viewModel.event.single()
                assert(event is IncrementCountEvent)
            }
            job.cancel()
        }
    }

    @ExperimentalCoroutinesApi
    @Test
    fun checkLatestStateEmitted() {
        runTest {
            viewModel.dispatch(DecrementCountEvent(1))
            viewModel.dispatch(IncrementCountEvent(1))
            viewModel.dispatch(IncrementCountEvent(1))
            assert(viewModel.currentState == TestState(1))
        }
    }

    @Test
    fun clearTest() {
        viewModel.clear()
        assert(testStateManagerScope.isCleared())
    }
}


