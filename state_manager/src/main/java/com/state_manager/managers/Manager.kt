package com.state_manager.managers

import androidx.annotation.CallSuper
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.state_manager.events.AppEvent
import com.state_manager.extensions.collectInScope
import com.state_manager.logger.androidLogger
import com.state_manager.logger.enableLogging
import com.state_manager.logger.logd
import com.state_manager.logger.logv
import com.state_manager.reducer.action
import com.state_manager.reducer.effects
import com.state_manager.reducer.reducer
import com.state_manager.side_effects.SideEffect
import com.state_manager.state.AppState
import com.state_manager.store.StateStoreFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull

abstract class Manager<S : AppState, E : AppEvent, SIDE_EFFECT : SideEffect>(
    val initialState: S,
) : ViewModel(){

    val coroutineScope: CoroutineScope = viewModelScope

    open val logger = androidLogger(this::class.java.simpleName + " StateStore")

    /**
     * The state store associated with this ViewModel
     */
    open var stateStore = StateStoreFactory.create<S, E, SIDE_EFFECT>(
        initialState,
        androidLogger(this::class.java.simpleName + " StateStore"),
        coroutineScope
    )

    /**
     * A [kotlinx.coroutines.flow.Flow] of [AppState] which can be observed by external classes to respond to changes in state.
     */
    private val state: Flow<S> = stateStore.stateObservable

    /**
     * A [kotlinx.coroutines.flow.Flow] of [AppEvent] which can be observed by external classes to respond to changes in state.
     */
    open val eventEmitter: Flow<E> = stateStore.eventObservable.filterNotNull()

    /**
     * Access the current value of state stored in the [stateStore].
     *
     * **THIS VALUE OF STATE IS NOT GUARANTEED TO BE UP TO DATE**
     * This property is only meant to be used by external classes who need to get hold of the current state
     * without having to subscribe to it. For use cases where the current state is needed to be accessed inside the
     * ViewModel, the [withState] method should be used
     */
    val currentState: S
        get() = stateEmitter.value

    val stateEmitter: StateFlow<S> = stateStore.stateObservable

    abstract fun onEvent(event: E, state: S)

    init {
        log()
    }

    fun dispatch(event: E) {
        withState {
            stateStore.offerGetEvent(event)
            onEvent(event, it)
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
    fun setState(action: reducer<S>) {
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
    fun withState(action: action<S>) {
        stateStore.offerGetAction(action)
    }

    fun postSideEffect(effect: effects<SIDE_EFFECT>) = stateStore.offerSideEffect { effect()  }

    fun onSideEffect(): SharedFlow<SIDE_EFFECT?> = stateStore.effectObservable

    /**
     * Clears this ViewModel as well as its [stateStore].
     */
    @CallSuper
    override fun onCleared() {
        logger.logv { "Clearing ViewModel ${this::class}" }
        super.onCleared()
        stateStore.clear()
    }


    private fun log() {
        if (enableLogging) {
            stateEmitter.collectInScope(coroutineScope) {
                logger.logd { "State: $it" }
            }
            stateStore.eventObservable.collectInScope(coroutineScope) {
                it?.let { logger.logd { "Event: $it" } }
            }
        }
    }
}
