package com.core.platform.exception

import com.core.platform.exception.api_error.ApiError
import com.core.platform.exception.api_error.ServerError

/**
 * ErrorResult Represent Presentation or Domain layer exceptions (Note : for data layer we have [Failure]).
 * Possible Usage : 1. Use it to represent an exception that can be used in Ui State.
 *                  2. Use it to show proper error ui in the screens.
 */
sealed class ErrorResult : Exception()

/**
 * Represent No data available for a particular request.
 */
object NoDataError : ErrorResult()

/**
 * Represent Io Exceptions
 */
object NetworkUnavailable : ErrorResult()

/**
 * Represent Unknown Exceptions
 */
object UnknownErrorResult : ErrorResult()

/**
 * Represent error in request parameter [Http Status Code - 400..499](https://developer.mozilla.org/en-US/docs/Web/HTTP/Status#client_error_responses)
 */
data class ClientRequestErrorResult(val error: ApiError) : ErrorResult()

/**
 * Represent error in server []Http Status Code - 500..599](https://developer.mozilla.org/en-US/docs/Web/HTTP/Status#client_error_responses)
 */
data class ServerResponseErrorResult(val error: ServerError) : ErrorResult()

