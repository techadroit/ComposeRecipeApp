package com.state_manager.test

import com.state_manager.scopes.StateManagerCoroutineScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineScheduler
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher

class TestStateManagerScope(coroutineScheduler: TestCoroutineScheduler = TestCoroutineScheduler()) : StateManagerCoroutineScope {
    val testJob = Job()

    @OptIn(ExperimentalCoroutinesApi::class)
    val testScope = TestScope(UnconfinedTestDispatcher(coroutineScheduler) + testJob)
    override fun getScope(): CoroutineScope = testScope

    override fun isCleared() = !testScope.isActive && testJob.isCancelled

    override fun run(fn: suspend () -> Unit): Job {
        return testScope.launch {
//            runBlocking {
                fn.invoke()
//            }
        }
    }
}