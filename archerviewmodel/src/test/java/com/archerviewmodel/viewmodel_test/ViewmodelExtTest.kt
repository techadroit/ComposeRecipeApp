package com.archerviewmodel.viewmodel_test

import com.archerviewmodel.BaseUnitTest
import com.archerviewmodel.viewmodel.BaseArchViewModel
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.spyk
import kotlinx.coroutines.awaitCancellation
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.Test


private const val TASK_A = "task_a"

internal class ViewModelExtTest : BaseUnitTest() {

    @Test
    fun testStartJobIdempotent_IndefiniteTask() = runBlocking {
        /* This test ensures that starting a new job with `replaceCurrent` = false
         * DOES NOT preempt the current running job with a new one. */
        val viewModel = mockk<BaseArchViewModel>().also {
            every { it.coroutineScope } returns this
        }
        val suspendTask = spyk(IndefiniteSuspension())
        val runAttempts = 1000

        repeat(runAttempts) {
            viewModel.startJob(TASK_A) {
                suspendTask.run()
            }
        }
        viewModel.stopAllJobs()

        coVerify(atMost = 1) { suspendTask.run() }
    }

    @Test
    fun testStartJobIdempotent_DefiniteTask() = runBlocking {
        /* This test ensures that starting a new job with `replaceCurrent` = false
         * DOES NOT preempt the current running job with a new one.
         * And for sufficiently short tasks, a new one is started if the previous is done. */
        val viewModel = mockk<BaseArchViewModel>().also {
            every { it.coroutineScope } returns this
        }
        val suspendTask = spyk(ShortSuspension())
        val runAttempts = 10

        repeat(runAttempts) {
            viewModel.startJob(TASK_A) {
                suspendTask.run()
            }
            delay(suspendTask.delayLength + 10) // Give the short task time to finish
        }
        viewModel.stopAllJobs()

        coVerify(atLeast = runAttempts) { suspendTask.run() }
    }

    @Test
    fun testStartJobPreemption_IndefiniteTask() = runBlocking {
        /* This test ensures that starting a new job with `replaceCurrent` = true
         * preempts the current running job with a new one. */
        val viewModel = mockk<BaseArchViewModel>().also {
            every { it.coroutineScope } returns this
        }
        val suspendTask = spyk(IndefiniteSuspension())
        val runAttempts = 10
        repeat(runAttempts) {
            viewModel.startJob(TASK_A, replaceCurrent = true) { suspendTask.run() }
            delay(100)
        }
        viewModel.stopAllJobs()
        coVerify(atLeast = runAttempts) { suspendTask.run() }
    }

    @Test
    fun testStartJobPreemption_DefiniteTask() = runBlocking {
        /* This test ensures that starting a new job with `replaceCurrent` = true
         * preempts the current running job with a new one. */
        val viewModel = mockk<BaseArchViewModel>().also {
            every { it.coroutineScope } returns this
        }
        val suspendTask = spyk(ShortSuspension())
        val runAttempts = 10
        repeat(runAttempts) {
            viewModel.startJob(TASK_A, replaceCurrent = true) { suspendTask.run() }
            delay(suspendTask.delayLength / 2) // Shorten delay, start next before previous has time to finish.
        }
        viewModel.stopAllJobs()
        coVerify(atLeast = runAttempts) { suspendTask.run() }
    }

    /**
     * ShortSuspension
     *
     * Suspends for 100ms to simulate a network or database call.
     */
    private class ShortSuspension() {
        val delayLength = 100L
        suspend fun run() {
            delay(delayLength)
        }
    }

    /**
     * IndefiniteSuspension
     *
     * Suspends until the job is cancelled. Mimics a `Flow.collect` on an inexhaustible resource.
     */
    private class IndefiniteSuspension() {
        suspend fun run() {
            awaitCancellation()
        }
    }
}

