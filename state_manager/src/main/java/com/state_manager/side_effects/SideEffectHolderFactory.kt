package com.state_manager.side_effects

import com.state_manager.logger.Logger
import kotlinx.coroutines.CoroutineScope

internal object SideEffectHolderFactory {

    fun <S: SideEffect> create(logger: Logger,scope: CoroutineScope): SideEffectHolder<S>{
        return SideEffectHolderImpl(logger,scope)
    }
}