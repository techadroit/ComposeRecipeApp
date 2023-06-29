package com.state_manager.state

import com.state_manager.logger.Logger
import com.state_manager.logger.logv
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

internal class StateHolderImpl<S : AppState>(
    initialState: S,
    private val logger: Logger
) : StateHolder<S> {

    private val _stateObservable = MutableStateFlow(initialState)

    override val stateObservable: StateFlow<S>
        get() = _stateObservable

    override fun updateState(state: S) {
        _stateObservable.value = state
    }

    override fun clearHolder() {
        logger.logv { "Clearing State Holder" }
        // StateFlow does not need to be closed
    }
}
