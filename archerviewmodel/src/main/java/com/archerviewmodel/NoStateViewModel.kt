package com.archerviewmodel

import com.archerviewmodel.state.EmptyState

/**
 * ViewModel which doesn't force event state structure
 */
class NoStateViewModel : NoEventViewModel<EmptyState>(initialState = EmptyState())
