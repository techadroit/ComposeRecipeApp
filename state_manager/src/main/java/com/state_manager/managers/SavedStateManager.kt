package com.state_manager.managers

import androidx.lifecycle.SavedStateHandle
import com.state_manager.events.AppEvent
import com.state_manager.logger.logd
import com.state_manager.scopes.StateManagerCoroutineScope
import com.state_manager.side_effects.SideEffect
import com.state_manager.state.AppState

/**
 * A Subclass of [Manager] that has access to a [SavedStateHandle] to easily
 * persist state properties in case of process death
 *
 * @param initialState The initial state for this ViewModel
 * @param coroutineScope The Scope to be used with the contained State Store
 * @param savedStateHandle The [SavedStateHandle] to be used for persisting state across process deaths
 */
abstract class SavedStateManager<S : AppState,E: AppEvent>(
    initialState: S,
    coroutineScope: StateManagerCoroutineScope,
    protected val savedStateHandle: SavedStateHandle
) : Manager<S,E,SideEffect>(initialState) {

    companion object {
        /**
         * A predefined key which can be used to persist a valid [AppState] class into the
         * [savedStateHandle]
         */
        const val KEY_SAVED_STATE = "appstate:saved-state"
    }

    /**
     * A convenience wrapper around the [setState] function which runs the given reducer, and then
     * persists the newly created state
     *
     * @param reducer The state reducer to create a new state from the current state
     *
     */
    protected fun setStateAndPersist(reducer: suspend S.() -> S) {
        setState(reducer)
        persistState()
    }

    /**
     * Saves the current state into [savedStateHandle] using [KEY_SAVED_STATE]
     * Subclasses can override this method for custom behaviour.
     */
    protected open fun persistState() = withState { state ->
        logger.logd { "Persisting state: $state" }
        savedStateHandle.set(KEY_SAVED_STATE, state)
    }
}