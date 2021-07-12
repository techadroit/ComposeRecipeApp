package com.archerviewmodel

import androidx.annotation.CallSuper
import androidx.lifecycle.ViewModel
import com.archerviewmodel.events.ArcherEvent
import com.archerviewmodel.reducer.action
import com.archerviewmodel.reducer.reducer
import com.archerviewmodel.state.ArcherState
import com.archerviewmodel.store.StateStoreFactory
import com.example.composerecipeapp.core.logger.Logger
import com.example.composerecipeapp.core.logger.androidLogger
import com.example.composerecipeapp.core.logger.logd
import com.example.composerecipeapp.core.logger.logv
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import kotlin.coroutines.CoroutineContext

abstract class ArcherViewModel<S : ArcherState, E : ArcherEvent>(
    initialState: S,
    stateStoreContext: CoroutineContext = Dispatchers.Default + SupervisorJob()
) : ViewModel() {

    protected val logger: Logger by lazy { androidLogger(tag = this::class.java.simpleName) }
    private val eventFlow = emptyFlow<E>()
    val viewModelScope by lazy {
        CoroutineScope(stateStoreContext)
    }

    /**
     * The state store associated with this ViewModel
     */
    protected val stateStore = StateStoreFactory.create<S,E>(
        initialState,
        androidLogger(this::class.java.simpleName + "StateStore"),
        stateStoreContext
    )

    /**
     * A [kotlinx.coroutines.flow.Flow] of [ArcherState] which can be observed by external classes to respond to changes in state.
     */
    val state: Flow<S> = stateStore
        .stateObservable
        .onEach { state ->
            logger.logd { "New state: $state" }
        }

    /**
     * A [kotlinx.coroutines.flow.Flow] of [ArcherEvent] which can be observed by external classes to respond to changes in state.
     */
    val event: Flow<E> = stateStore.eventObservable.onEach {
        logger.logd { "New Event: $it" }
    }.filterNotNull()

    /**
     * Access the current value of state stored in the [stateStore].
     *
     * **THIS VALUE OF STATE IS NOT GUARANTEED TO BE UP TO DATE**
     * This property is only meant to be used by external classes who need to get hold of the current state
     * without having to subscribe to it. For use cases where the current state is needed to be accessed inside the
     * ViewModel, the [withState] method should be used
     */
    val currentState: S
        get() = stateStore.state

    val stateEmitter: StateFlow<S> = stateStore.stateObservable

    abstract fun onEvent(event: E,state: S)

    fun dispatch(event: E) {
        withState {
            stateStore.offerGetEvent(event)
            onEvent(event,it)
        }
    }

    /**
     * The only method through which state mutation is allowed in subclasses.
     *
     * Dispatches an action the [stateStore]. This action shall be processed as soon as possible in
     * the state store, but not necessarily immediately
     *
     * @param action The state reducer to create a new state from the current state
     *
     */
    protected fun setState(action: reducer<S>) {
        stateStore.offerSetAction(action)
    }

    /**
     * Dispatch the given action the [stateStore]. This action shall be processed as soon as all existing
     * state reducers have been processed. The state parameter supplied to this action should be the
     * latest value at the time of processing of this action.
     *
     * These actions are treated as side effects. A new coroutine is launched for each such action, so that the state
     * processor does not get blocked if a particular action takes too long to finish.
     *
     * @param action The action to be performed with the current state
     *
     */
    protected fun withState(action: action<S>) {
        stateStore.offerGetAction(action)
    }

    /**
     * Clears this ViewModel as well as its [stateStore].
     */
    @CallSuper
    override fun onCleared() {
        logger.logv { "Clearing ViewModel" }
        super.onCleared()
        stateStore.clear()
    }

}




