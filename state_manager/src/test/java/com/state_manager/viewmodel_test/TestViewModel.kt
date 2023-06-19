package com.state_manager.viewmodel_test

import com.state_manager.managers.StateEventManager
import com.state_manager.scopes.StateManagerCoroutineScope

class TestViewModel(val initialState: TestState, coroutineScope: StateManagerCoroutineScope) :
    StateEventManager<TestState, TestEvent>(
        initialState = initialState,
        coroutineScope = coroutineScope
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