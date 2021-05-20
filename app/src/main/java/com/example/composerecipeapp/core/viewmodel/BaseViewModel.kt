package com.example.composerecipeapp.core.viewmodel

import android.util.Log
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.emptyFlow

abstract class BaseViewModel<S : State, E : Event>(initialState: S) : ViewModel() {

    private val stateFlow: MutableStateFlow<S> = MutableStateFlow(initialState)
    val stateEmitter: StateFlow<S> = stateFlow
    val eventFlow = emptyFlow<E>()
    val viewModelScope by lazy {
        CoroutineScope(Dispatchers.IO) + SupervisorJob()
    }

    abstract fun onEvent(event: E)

    protected fun setState(action: suspend S.() -> S) {
        viewModelScope.launch {
            val state = stateFlow.value
            val newState = action.invoke(state)
            stateFlow.value = newState
            Log.v("State ",": $newState")
        }
    }

    protected fun withState(action: (S) -> Unit) {
        action.invoke(stateFlow.value)
    }

    fun add(event: E) {
        onEvent(event)
    }

}

interface State
interface Event
