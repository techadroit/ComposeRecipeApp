package com.state_manager.viewmodel_test

import com.state_manager.BaseUnitTest
import com.state_manager.extensions.createTestContainer
import com.state_manager.extensions.verifyState
import com.state_manager.test.StateManagerTestRule
import com.state_manager.test.TestStateManagerScope
import com.state_manager.test.expect
import com.state_manager.test.expectNotEmpty
import com.state_manager.test.test
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineScheduler
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
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
        viewModel.createTestContainer().test {
            forEvents(IncrementCountEvent(1),IncrementCountEvent(1))
            verify {
                expectNotEmpty()
            }
        }
    }

    @ExperimentalCoroutinesApi
    @Test
    fun checkListOfStates2() {
        val states = listOf(TestState(), TestState(0, true), TestState(5))
        viewModel.createTestContainer().test {
            forEvents(IncrementCountEvent(5))
            verify {
                expect(states)
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
        viewModel.createTestContainer().test {
            forEvents(DecrementCountEvent(5),IncrementCountEvent(10))
            verify {
                expect(states)
            }
        }
    }

//    @Test
//    fun clearTest() {
//        viewModel.clear()
//        assert(testStateManagerScope.isCleared())
//    }
}

