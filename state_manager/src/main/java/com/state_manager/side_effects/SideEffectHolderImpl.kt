package com.state_manager.side_effects

import com.state_manager.logger.Logger
import com.state_manager.logger.logd
import com.state_manager.logger.logv
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SideEffectHolderImpl<SIDE_EFFECT : SideEffect>(
    private val logger: Logger,
    private val coroutineScope: CoroutineScope
) :
    SideEffectHolder<SIDE_EFFECT> {

//    private val _channel = Channel<SIDE_EFFECT>(capacity = 0)
//
//    override val effectObservable: StateFlow<SIDE_EFFECT?> =
//        _channel.receiveAsFlow().stateIn(
//            scope = coroutineScope,
//            started = SharingStarted.WhileSubscribed(),
//            initialValue = null
//        )

    private val _sharedFlow = MutableSharedFlow<SIDE_EFFECT>()

    override var effectObservable: SharedFlow<SIDE_EFFECT?> = _sharedFlow
        private set

    override fun clearEffectHolder() {
        logger.logv { "Clearing SideEffect Holder" }
    }

    override fun post(sideEffect: SIDE_EFFECT) {
        coroutineScope.launch {
            _sharedFlow.emit(sideEffect)
        }
        logger.logd { "SideEffect: $sideEffect" }
    }
}