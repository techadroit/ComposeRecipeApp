package com.state_manager.extensions

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestCoroutineScheduler
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher

class TestObserver<S>(
    scope: CoroutineScope,
    testCoroutineScheduler: TestCoroutineScheduler,
    flow: StateFlow<S>
) {
    private val values = mutableListOf<S>()

    @OptIn(ExperimentalCoroutinesApi::class)
    private val job: Job = scope.launch(UnconfinedTestDispatcher(testCoroutineScheduler)) {
        flow.collect {
            values.add(it)
        }
    }

    fun assertValues(vararg values: S): TestObserver<S> {
        assert(values.toList() == values)
        return this
    }
}

fun <S> StateFlow<S>.testAll(scope:TestScope): TestObserver<S> {
        return TestObserver(scope,scope.testScheduler,this)
}