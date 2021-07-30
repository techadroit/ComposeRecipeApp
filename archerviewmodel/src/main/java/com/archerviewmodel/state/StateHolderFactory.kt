package com.archerviewmodel.state

import com.example.composerecipeapp.core.logger.Logger

internal object StateHolderFactory {

    /**
     * Creates and returns a [StateHolder].
     *
     * @param initialState The initial state to be passed to the state holder
     * @param logger The logger to be used by the state holder for debug logs
     *
     * @return A class that implements the state holder interface
     */
    fun <S : ArcherState> create(initialState: S, logger: Logger): StateHolder<S> {
        return StateHolderImpl(initialState, logger)
    }
}
