package com.state_manager.viewmodel_test

import androidx.lifecycle.viewModelScope
import com.state_manager.managers.StateEventManager
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class TestViewModel(val initialTestState: TestState) :
    StateEventManager<TestState, TestEvent>(
        initialState = initialTestState,
    ) {


    override fun onEvent(event: TestEvent, state: TestState) {
        when (event) {
            is IncrementCountEvent ->
                increment(event.counter)


            is DecrementCountEvent ->
                setState { this.copy(counter = this.counter - event.counter) }

        }
    }

    fun increment(counter: Int) {
        setState {
            copy(isSetting = true)
        }
        viewModelScope.launch {
            delay(100)
            setState { this.copy(counter = this.counter + counter, isSetting = false) }
            postSideEffect { SuccessUpdate(currentState.counter) }
        }
    }

    fun clear() {
        onCleared()
    }
}