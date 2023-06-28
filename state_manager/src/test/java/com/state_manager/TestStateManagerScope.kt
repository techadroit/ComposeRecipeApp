package com.state_manager

import com.state_manager.scopes.StateManagerCoroutineScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.isActive
import kotlinx.coroutines.test.TestCoroutineScheduler
import kotlinx.coroutines.test.UnconfinedTestDispatcher

class TestStateManagerScope: StateManagerCoroutineScope {
    val testJob = Job()
    @OptIn(ExperimentalCoroutinesApi::class)
    val testScope = CoroutineScope(UnconfinedTestDispatcher(TestCoroutineScheduler()))
    override fun getScope(): CoroutineScope = testScope

    fun isCleared() = !testScope.isActive && testJob.isCancelled
}