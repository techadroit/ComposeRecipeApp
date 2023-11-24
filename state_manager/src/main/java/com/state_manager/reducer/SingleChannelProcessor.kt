package com.state_manager.reducer

import com.state_manager.events.AppEvent
import com.state_manager.events.EventHolder
import com.state_manager.logger.Logger
import com.state_manager.logger.logd
import com.state_manager.logger.logv
import com.state_manager.side_effects.SideEffect
import com.state_manager.side_effects.SideEffectHolder
import com.state_manager.state.AppState
import com.state_manager.state.StateHolder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.selects.select

internal class SingleChannelProcessor<S : AppState, E : AppEvent, SIDE_EFFECT : SideEffect>(
    shouldStartImmediately: Boolean = false,
    private val stateHolder: StateHolder<S>,
    private val eventHolder: EventHolder<E>,
    private val sideEffectHolder: SideEffectHolder<SIDE_EFFECT>,
    private val logger: Logger,
    private val processorScope: CoroutineScope
) : StateProcessor<S, E, SIDE_EFFECT> {

    private val channel: Channel<JobIntent<S, E, SIDE_EFFECT>> = Channel(Channel.UNLIMITED)

    init {
        if (shouldStartImmediately) {
            start()
        } else {
            logger.logv { "Starting in Lazy mode. Call start() to begin processing actions and reducers" }
        }
    }

    override fun offerSetAction(reducer: reducer<S>) {
        processorScope.launch {
            if (processorScope.isActive && !channel.isClosedForSend) {
                channel.trySend(JobIntent.Reducer(reducer) as JobIntent<S, E, SIDE_EFFECT>)
            }
        }
    }

    override fun offerGetAction(action: action<S>) {
        processorScope.launch {
            if (processorScope.isActive && !channel.isClosedForSend) {
                channel.trySend(JobIntent.Action(action) as JobIntent<S, E, SIDE_EFFECT>)
            }
        }
    }

    override fun offerSideEffect(effects: effects<SIDE_EFFECT>) {
        processorScope.launch {
            if (processorScope.isActive && !channel.isClosedForSend) {
                channel.trySend(JobIntent.Effects(effects) as JobIntent<S, E, SIDE_EFFECT>)
            }
        }
    }

    override fun offerGetEvent(event: E) {
        processorScope.launch {
            eventHolder.addEvent(event)
        }
    }

    override fun clearProcessor() {
        if (processorScope.isActive) {
            logger.logd { "Clearing StateProcessor $this" }
            processorScope.cancel()
            channel.close()
        }
    }

    internal fun start() = processorScope.launch {
        while (isActive) {
            selectJob()
        }
    }

    private suspend fun selectJob(sideEffectScope: CoroutineScope = processorScope) {
        select<Unit> {
            channel.onReceive { job ->
                when (job) {
                    is JobIntent.Reducer -> {
                        val newState = job.reducer(stateHolder.state)
                        stateHolder.updateState(newState)
                    }

                    is JobIntent.Action -> {
                        job.action.invoke(stateHolder.state)
                    }

                    is JobIntent.Effects -> {
                        val effect = job.effects()
                        sideEffectHolder.post(effect)
                    }
                }
            }
        }
    }

    override suspend fun drain(scope: CoroutineScope) {
        do {
            while (!channel.isEmpty) {
                selectJob(sideEffectScope = scope)
            }
        } while (!channel.isEmpty)
    }
}

sealed class JobIntent<S : AppState, E : AppEvent, SIDE_EFFECT : SideEffect> {

    data class Reducer<S : AppState>(val reducer: reducer<S>) : JobIntent<S, Nothing, Nothing>()
    data class Action<S : AppState>(val action: action<S>) : JobIntent<S, Nothing, Nothing>()
    data class Effects<SIDE_EFFECT : SideEffect>(val effects: effects<SIDE_EFFECT>) :
        JobIntent<Nothing, Nothing, SIDE_EFFECT>()
}

