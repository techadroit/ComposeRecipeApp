package com.state_manager.viewmodel_test

import com.state_manager.managers.StateEventManager
import com.state_manager.scopes.StateManagerCoroutineScope
import kotlinx.coroutines.delay

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
               increment(event.counter)
            }
            is DecrementCountEvent -> run {
                setState { this.copy(counter = this.counter - event.counter) }
            }
        }
    }

    fun increment(counter:Int){
        setState {
            copy(isSetting = true)
        }
        coroutineScope.run{
//            delay(100)
            setState { this.copy(counter = this.counter + counter,isSetting = false) }
        }
    }

    fun clear() {
        onCleared()
    }
}