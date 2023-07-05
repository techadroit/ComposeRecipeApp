package com.state_manager.test

import com.state_manager.scopes.StateManagerCoroutineScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher

class TestStateManagerScope @OptIn(ExperimentalCoroutinesApi::class)
constructor(val testDispatcher: TestDispatcher = UnconfinedTestDispatcher()) :
    StateManagerCoroutineScope(testDispatcher) {
    val testJob = Job()
    val testScope = TestScope(testDispatcher + testJob)
    override fun getScope(): CoroutineScope = testScope

    override fun isCleared() = !testScope.isActive && testJob.isCancelled

//    override fun run(fn: suspend () -> Unit): Job {
//        return testScope.launch {
//            fn.invoke()
//        }
//    }
}