package com.core.platform.exception

import com.core.platform.exception.Failure.FeatureFailure

/**
 * Base Class for handling errors/failures/exceptions.
 * Every feature specific failure should extend [FeatureFailure] class.
 */
sealed class Failure : Exception() {
    object NetworkConnection : Failure()
    object ServerError : Failure()
    object Unauthorized : Failure()
    object UnknonwnError : Failure()

    /** * Extend this class for feature specific failures.*/
    abstract class FeatureFailure : Failure()
}

object NoSavedRecipe : Failure.FeatureFailure()
