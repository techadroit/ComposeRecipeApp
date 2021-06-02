package com.example.composerecipeapp.core.viewmodel

import com.example.composerecipeapp.core.logger.Logger
import com.example.composerecipeapp.core.logger.logd
import com.example.composerecipeapp.core.logger.logv
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow


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
