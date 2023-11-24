package com.state_manager.viewmodel_test

import com.state_manager.BaseUnitTest
import com.state_manager.events.EventHolder
import com.state_manager.events.EventHolderImpl
import com.state_manager.reducer.SelectBasedStateProcessor
import com.state_manager.state.StateHolder
import com.state_manager.state.StateHolderFactory
import com.state_manager.logger.systemOutLogger
import com.state_manager.side_effects.SideEffect
import com.state_manager.side_effects.SideEffectHolderImpl
import com.state_manager.test.TestStateManagerScope
import kotlinx.coroutines.*
import org.junit.After
import org.junit.Before
import org.junit.Test

internal class SelectBasedStateProcessorTest : BaseUnitTest() {

    private lateinit var holder: StateHolder<CountingState>
    private lateinit var eventHolder: EventHolder<CountingEvent>
    private lateinit var processor: SelectBasedStateProcessor<CountingState, CountingEvent,SideEffect>
    val testStateManagerScope = TestStateManagerScope()

    @Before
    fun setup() {
        holder = StateHolderFactory.create(CountingState(), systemOutLogger())
        eventHolder = EventHolderImpl(systemOutLogger())
        processor = SelectBasedStateProcessor(
            shouldStartImmediately = false,
            stateHolder = holder,
            eventHolder = eventHolder,
            sideEffectHolder = SideEffectHolderImpl(systemOutLogger(),testStateManagerScope.getScope()),
            logger = systemOutLogger(),
            processorScope = testStateManagerScope.getScope()
        )
    }

    @After
    fun clear() {
        processor.clearProcessor()
        holder.clearHolder()
    }

    @Test
    fun `when new state is set, it should store it to state holder`() = runBlocking {
        processor.offerSetAction {
            copy(count = 42)
        }
        processor.drain(this)
        assert(holder.state.count == 42)
    }

    @Test
    fun `when new event is added, it should store it to event holder`() = runBlocking {
        processor.offerGetEvent(CountingEvent(42))
        processor.drain(this)
        assert(eventHolder.event == CountingEvent(42))
    }

    @Test
    fun `when multiple jobs are enqueued, should process state reducers before actions`() =
        runBlocking {
            val reducerValue = "reducer-first"
            val actionValue = "action-first"

            val valueHolder = CompletableDeferred<String>()
            processor.offerSetAction {
                val newState = copy(count = 42)
                valueHolder.complete(reducerValue)
                newState
            }

            processor.offerGetAction {
                valueHolder.complete(actionValue)
            }
            processor.drain(this)

            val valueSetFirst = valueHolder.await()
            assert(valueSetFirst == reducerValue)
        }

    @Test
    fun `when a job produces more reducers, then they should be processed before any actions`() =
        runBlocking {
            val secondReducerValue = "reducer-second"
            val secondActionValue = "action-second"

            val valueHolder = CompletableDeferred<String>()
            processor.offerSetAction {
                processor.offerGetAction {
                    valueHolder.complete(secondActionValue)
                }
                processor.offerSetAction {
                    valueHolder.complete(secondReducerValue)
                    this
                }
                this
            }
            processor.drain(this)

            val valueSetFirst = valueHolder.await()
            assert(valueSetFirst == secondReducerValue)
        }

    @Test
    fun `state should remain consistent even when there are multiple sources of jobs`() =
        runBlocking {

            val iterations = 10
            val additionJobsCompletable = CompletableDeferred<Unit>()
            val subtractionJobsCompletable = CompletableDeferred<Unit>()

            val incrementActionsSourceJob = async {
                repeat(iterations) { i ->
                    processor.offerSetAction {
                        copy(count = count + 1).also {
                            if (i == iterations - 1) additionJobsCompletable.complete(
                                Unit
                            )
                        }
                    }
                    yield()
                }
            }

            val decrementActionsSourceJob = async {
                repeat(2 * iterations) { i ->
                    processor.offerSetAction {
                        copy(count = count - 1).also {
                            if (i == (2 * iterations) - 1) subtractionJobsCompletable.complete(
                                Unit
                            )
                        }
                    }
                    yield()
                }
            }

            processor.start()

            awaitAll(
                additionJobsCompletable,
                subtractionJobsCompletable
            )

            assert(holder.state.count == -iterations)
        }

    @Test
    fun `jobs sent after processor is cleared should be ignored`() = runBlocking {
        processor.start()
        processor.clearProcessor()
        var count = 0
        processor.offerGetAction {
            count++
        }

        processor.offerSetAction {
            count++
            this
        }

        assert(count == 0) {
            """Count value changed when it should not have been. Expected 0, got $count
                |State Processor is not ignoring jobs sent to it after it has been cleared""".trimMargin()
        }
    }

    @Test
    fun `clear operation should be idempotent`() = runBlocking {
        processor.start()
        repeat(10) { processor.clearProcessor() }
    }

    @Test
    fun `should not access state from StateHolder if it has been cancelled`() = runBlocking {
        holder.clearHolder()
        processor.offerGetAction {
            // No-op
        }
        processor.offerSetAction {
            copy(count = count + 1)
        }
        processor.drain(this)
        // If there are no errors, test is successful
    }

    @Test
    fun `should not drain after StateProcessor is cleared`() = runBlocking {
        processor.clearProcessor()
        processor.offerSetAction {
            copy(count = count + 1)
        }
        // Draining the processor after it is cleared should throw JobCancellationException
        processor.drain(this)
        assert(holder.state.count == 0) {
            "State reducer was processed by drainAsync after the StateProcessor was cleared"
        }
    }
}
