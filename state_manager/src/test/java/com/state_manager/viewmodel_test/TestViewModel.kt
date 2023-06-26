package com.state_manager.viewmodel_test

import com.state_manager.managers.StateEventManager
import com.state_manager.scopes.StateManagerCoroutineScope

class TestViewModel(val initialTestState: TestState, coroutineScope: StateManagerCoroutineScope) :
    StateEventManager<TestState, TestEvent>(
        initialState = initialTestState,
        coroutineScope = coroutineScope
    ) {

        init {
            dispatch(IncrementCountEvent(1))
        }

    override fun onEvent(event: TestEvent, state: TestState) {
        when (event) {
            is IncrementCountEvent -> run {
                setState { this.copy(counter = this.counter + event.counter) }
            }
            is DecrementCountEvent -> run{
                setState { this.copy(counter = this.counter - event.counter) }
            }
        }
    }

    fun clear() {
        onCleared()
    }
}