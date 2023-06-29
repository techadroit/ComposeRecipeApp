package com.state_manager.scopes

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

/**
 * A coroutine scope for state managers that provides utility functions for managing coroutines.
 */
interface StateManagerCoroutineScope {

    /**
     * Returns the underlying CoroutineScope instance.
     */
    fun getScope(): CoroutineScope

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

    fun isCleared(): Boolean

    /**
     * Runs a suspend function within the coroutine scope.
     *
     * @param fn the suspend function to be executed.
     */
    fun run(fn: suspend () -> Unit) = getScope().launch {
        fn()
    }
}