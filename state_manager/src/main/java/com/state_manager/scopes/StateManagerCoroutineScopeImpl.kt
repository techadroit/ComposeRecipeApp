package com.state_manager.scopes

import android.util.Log
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.isActive

class StateManagerCoroutineScopeImpl(dispatcher: CoroutineDispatcher = Dispatchers.Default) :
    StateManagerCoroutineScope(dispatcher) {

    val coroutineContext = dispatcher + SupervisorJob()

    private val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        exception.printStackTrace()
        exception.message?.let { Log.d("Error", it) }
    }

    val coroutineScope by lazy {
        CoroutineScope(coroutineContext + exceptionHandler)
    }

    override fun getScope(): CoroutineScope = coroutineScope

    override fun isCleared() = !getScope().isActive
}