package com.state_manager.scopes

import android.util.Log
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlin.coroutines.CoroutineContext

class StateManagerCoroutineScopeImpl(coroutineContext: CoroutineContext) : StateManagerCoroutineScope {

    private val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        exception.printStackTrace()
        exception.message?.let { Log.d("Error", it) }
    }

    val coroutineScope by lazy {
        CoroutineScope(coroutineContext + exceptionHandler)
    }
    override fun getScope(): CoroutineScope = coroutineScope
}