package com.state_manager.scopes

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

interface StateManagerCoroutineScope {

    fun getScope(): CoroutineScope

    fun isActive() = getScope().isActive

    fun cancel() = getScope().cancel()

    fun run(fn: suspend () -> Unit) = getScope().launch {
        fn()
    }
}