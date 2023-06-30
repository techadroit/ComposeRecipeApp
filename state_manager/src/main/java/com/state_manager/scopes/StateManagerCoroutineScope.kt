package com.state_manager.scopes

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

/**
 * A coroutine scope for state managers that provides utility functions for managing coroutines.
 */
abstract class StateManagerCoroutineScope(val dispatcher: CoroutineDispatcher) {

    /**
     * Returns the underlying CoroutineScope instance.
     */
    abstract fun getScope(): CoroutineScope

    /**
     * Checks if the coroutine scope is active.
     *
     * @return true if the coroutine scope is active, false otherwise.
     */
    fun isActive() = getScope().isActive

    /**
     * Cancels the coroutine scope and all its child coroutines.
     */
    fun cancel() = getScope().cancel()

    abstract fun isCleared(): Boolean

    /**
     * Runs a suspend function within the coroutine scope.
     *
     * @param fn the suspend function to be executed.
     */
    open fun run(fn: suspend () -> Unit) = getScope().launch {
        fn()
    }
}