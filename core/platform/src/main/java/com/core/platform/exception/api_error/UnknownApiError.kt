package com.core.platform.exception.api_error

/**
 * Represent the unhandled error response or exceptions
 *
 */
class UnknownApiError(val code: Int, override val message: String?) : Exception()