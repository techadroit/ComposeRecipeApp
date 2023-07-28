package com.core.platform.exception.api_error

/**
 * Represent the Client error responses (400 - 499)
 *
 * See [Client Error Response](https://developer.mozilla.org/en-US/docs/Web/HTTP/Status#client_error_responses)
 *
 */
data class ApiError(val code:Int,val message: String?)