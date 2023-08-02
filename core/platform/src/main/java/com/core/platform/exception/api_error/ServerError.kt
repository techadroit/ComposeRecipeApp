package com.core.platform.exception.api_error

/**
 * Represent the Server error responses (500 - 599)
 *
 * See [Server Error Response](https://developer.mozilla.org/en-US/docs/Web/HTTP/Status#server_error_responses)
 *
 */
data class ServerError(val code: Int, val message: String?)