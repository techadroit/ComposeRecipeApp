package com.archerviewmodel

import com.archerviewmodel.events.EmptyEvent
import com.archerviewmodel.state.ArcherState

/**
 * ViewModel Which doesn't enforce event driven Structure
 */
open class NoEventViewModel<T : ArcherState>(initialState: T) : ArcherViewModel<T, EmptyEvent>(initialState = initialState) {
    override fun onEvent(event: EmptyEvent, state: T) {
    }
}
