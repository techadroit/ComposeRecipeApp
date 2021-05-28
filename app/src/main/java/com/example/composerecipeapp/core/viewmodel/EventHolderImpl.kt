package com.example.composerecipeapp.core.viewmodel

import com.example.composerecipeapp.core.logger.Logger
import com.example.composerecipeapp.core.logger.logd
import com.example.composerecipeapp.core.logger.logv
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class EventHolderImpl<E : AppEvent?>(private val logger: Logger) : EventHolder<E> {

    private val _eventStateFlow = MutableStateFlow<E?>(null)

    override val eventObservable: StateFlow<E?>
        get() = _eventStateFlow

    override fun addEvent(newEvent: E) {
        logger.logd { "Event: $newEvent" }
        _eventStateFlow.value = newEvent
    }

    override fun clearHolder() {
        logger.logv { "Clearing Event Holder" }
        // StateFlow does not need to be closed
    }
}
