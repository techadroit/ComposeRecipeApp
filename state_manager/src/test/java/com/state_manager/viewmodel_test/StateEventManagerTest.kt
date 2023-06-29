package com.state_manager.viewmodel_test

import com.state_manager.BaseUnitTest
import com.state_manager.extensions.verifyState
import com.state_manager.test.TestStateManagerScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
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
    fun checkListOfStates() {
        viewModel.verifyState(
            IncrementCountEvent(1),
            IncrementCountEvent(1)
        ) {
            print(it)
            assert(it.isNotEmpty())
        }
    }

    @ExperimentalCoroutinesApi
    @Test
    fun checkListOfStates2() {
        runTest {
            val states = listOf(TestState(), TestState(0, true), TestState(5))
            viewModel.verifyState(
                IncrementCountEvent(5),
            ) {
                println(it)
                assertEquals(states, it)
            }
        }
    }

    @ExperimentalCoroutinesApi
    @Test
    fun checkListOfStates3() {
        val states = listOf(
            TestState(),
            TestState(-5),
            TestState(-5, isSetting = true),
            TestState(5, isSetting = false)
        )
        viewModel.verifyState(
            DecrementCountEvent(5),
            IncrementCountEvent(10)
        ) {
            print(it)
            assertEquals(states, it)
        }
    }

    @Test
    fun clearTest() {
        viewModel.clear()
        assert(testStateManagerScope.isCleared())
    }
}

