package com.state_manager

import com.state_manager.scopes.StateManagerCoroutineScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.isActive

class TestStateManagerScope: StateManagerCoroutineScope {
    val testJob = Job()
    val testScope = CoroutineScope( Dispatchers.Unconfined + testJob)
    override fun getScope(): CoroutineScope = testScope

    fun isCleared() = !testScope.isActive && testJob.isCancelled
}