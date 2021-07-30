package com.example.composerecipeapp

import com.archerviewmodel.ArcherViewModel
import com.archerviewmodel.events.ArcherEvent
import com.archerviewmodel.state.ArcherState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import kotlin.coroutines.CoroutineContext

internal class ArcherViewModelTest : BaseUnitTest() {
    private val job = Job()
    lateinit var viewModel: TestViewModel
    private val initialState = TestState()

    @ExperimentalCoroutinesApi
    @Before
    fun setUp() {
        viewModel = TestViewModel(initialState = initialState, testScope.coroutineContext + job)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun checkInitialState() {
        runBlockingTest {
            assert(viewModel.currentState == TestState())
        }
    }

    @ExperimentalCoroutinesApi
    @Test
    fun checkLatestEventReceived() {
        runBlockingTest {
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
        runBlockingTest {
            viewModel.dispatch(DecrementCountEvent(1))
            viewModel.dispatch(IncrementCountEvent(1))
            viewModel.dispatch(IncrementCountEvent(1))
            assert(viewModel.currentState == TestState(1))
        }
    }

    @Test
    fun clearTest() {
        viewModel.clear()
        assert(job.isCancelled)
    }
}

class TestViewModel(val initialState: TestState, coroutineContext: CoroutineContext) :
    ArcherViewModel<TestState, TestEvent>(
        initialState = initialState,
        stateStoreContext = coroutineContext
    ) {

    override fun onEvent(event: TestEvent, state: TestState) {
        when (event) {
            is IncrementCountEvent -> setState { this.copy(counter = this.counter + event.counter) }
            is DecrementCountEvent -> setState { this.copy(counter = this.counter - event.counter) }
        }
    }

    fun clear() {
        onCleared()
    }
}

interface TestEvent : ArcherEvent

data class IncrementCountEvent(val counter: Int) : TestEvent
data class DecrementCountEvent(val counter: Int) : TestEvent

data class TestState(val counter: Int = 0) : ArcherState
