package com.archerviewmodel.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.launch

open class BaseArchViewModel(val coroutineScope: CoroutineScope) : ViewModel() {


    private val jobs = mutableMapOf<String, Job>()

    /**
     * startJob
     *
     * Starts a job and associates it to the given key.
     *
     * If the job is already running, then a new one is not started.
     *
     * @param key name to be associated to the job. Only needs to be unique per View Model
     * @param replaceCurrent flag to determine, if any current running job should be stopped and replaced.
     * @param startJob suspend function that will be run as the job.
     */
    fun startJob(
        key: String,
        replaceCurrent: Boolean = false,
        startJob: suspend CoroutineScope.() -> Unit
    ) {
        val normalizedKey = keyForVM(key)
        val currentJob = jobs[normalizedKey]
        val jobIsAlive = currentJob?.isActive ?: false

        if (!jobIsAlive || replaceCurrent) {
            currentJob?.cancel()
            val newJob = coroutineScope
                .launch { startJob() }
            jobs[normalizedKey] = newJob
        }
    }

    /**
     * stopJob
     *
     * Stops the job associates with the given key, if it exists.
     *
     * @param key name to be associated to the job. Only needs to be unique per View Model.
     */
    fun stopJob(key: String) {
        val normalizedKey = keyForVM(key)
        coroutineScope.launch {
            jobs[normalizedKey]?.cancelAndJoin()
            jobs.remove(normalizedKey)
        }
    }

    /**
     * stopAllJobs
     *
     * Stops all job associated with the current view model
     */
    fun stopAllJobs() = coroutineScope.launch {
        jobs.filterKeys { it.startsWith(keyPrefix) }
            .forEach() { it.value.cancelAndJoin() }
    }

    /**
     * cleanJobs
     *
     * Clears inactive jobs from the view model out of the map of tasks for the view model.
     */
    fun cleanJobs() {
        jobs.filterKeys { it.startsWith(keyPrefix) }
            .filterValues { !it.isActive }
            .keys
            .forEach { key -> jobs.remove(key) }
    }

    /**
     * keyPrefix
     *
     * Key prefix for the view model. HashCode is used to differentiate multiple instances of the same
     * view model class.
     */
    private val keyPrefix get() = "${this::class.simpleName.orEmpty()}#${this.hashCode()}"

    /**
     * keyForVM
     *
     * Key for a task for the current view model.
     */
    private fun keyForVM(key: String): String = "$keyPrefix#$key"


}
