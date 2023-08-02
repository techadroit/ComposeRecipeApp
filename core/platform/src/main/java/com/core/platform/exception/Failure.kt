package com.core.platform.exception

import com.core.platform.exception.Failure.FeatureFailure

/**
 * Base Class for handling errors/failures/exceptions.
 * Every feature specific failure should extend [FeatureFailure] class.
 */
sealed class Failure : Exception() {
    object NetworkConnectionFailure : Failure()
    object ServerFailure : Failure()
    object UnauthorizedFailure : Failure()
    object UnknonwnFailure : Failure()
    object NoDataFailure : Failure()
    object ApiRequestFailure : Failure()

    /** * Extend this class for feature specific failures.*/
    abstract class FeatureFailure : Failure()
}

object NoSavedRecipeFailure : Failure.FeatureFailure()

fun Throwable.toFailure() = when (this) {
    is NoDataErrorResult -> Failure.NoDataFailure
    is NetworkUnavailableErrorResult -> Failure.NetworkConnectionFailure
    is ClientRequestErrorResult -> Failure.ApiRequestFailure
    is ServerResponseErrorResult -> Failure.ServerFailure
    else -> Failure.UnknonwnFailure
}
