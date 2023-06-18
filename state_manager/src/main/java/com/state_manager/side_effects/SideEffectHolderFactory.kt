package com.state_manager.side_effects

import com.state_manager.logger.Logger

internal object SideEffectHolderFactory {

    fun <S: SideEffect> create(logger: Logger): SideEffectHolder<S>{
        return SideEffectHolderImpl(logger)
    }
}