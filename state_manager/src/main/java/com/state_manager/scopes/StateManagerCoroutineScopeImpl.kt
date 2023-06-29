package com.state_manager.scopes

import android.util.Log
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlin.coroutines.CoroutineContext

class StateManagerCoroutineScopeImpl(coroutineContext: CoroutineContext = Dispatchers.Default + SupervisorJob()) : StateManagerCoroutineScope {

    private val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        exception.printStackTrace()
        exception.message?.let { Log.d("Error", it) }
    }

    val coroutineScope by lazy {
        CoroutineScope(coroutineContext + exceptionHandler)
    }
    override fun getScope(): CoroutineScope = coroutineScope
}