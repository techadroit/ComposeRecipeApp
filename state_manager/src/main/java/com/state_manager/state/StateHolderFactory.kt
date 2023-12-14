package com.state_manager.state

import com.state_manager.logger.Logger

object StateHolderFactory {

    /**
     * Creates and returns a [StateHolder].
     *
     * @param initialState The initial state to be passed to the state holder
     * @param logger The logger to be used by the state holder for debug logs
     *
     * @return A class that implements the state holder interface
     */
    fun <S : AppState> create(initialState: S, logger: Logger): StateHolder<S> {
        return StateHolderImpl(initialState, logger)
    }
}
