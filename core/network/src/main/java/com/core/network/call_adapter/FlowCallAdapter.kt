package com.core.network.call_adapter

import com.core.platform.exception.ClientRequestErrorResult
import com.core.platform.exception.NetworkUnavailable
import com.core.platform.exception.ServerResponseErrorResult
import com.core.platform.exception.api_error.ApiError
import com.core.platform.exception.api_error.ServerError
import com.core.platform.exception.api_error.UnknownApiError
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import retrofit2.Call
import retrofit2.CallAdapter
import java.io.IOException
import java.lang.reflect.Type
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class FlowCallAdapter<T>(private val responseType: Type) : CallAdapter<T, Flow<T>> {
    override fun responseType(): Type {
        return responseType
    }

    override fun adapt(call: Call<T>): Flow<T> =
        flow {
            try {
                val response = call.execute()
                when (response.code()) {
                    in 200..299 -> emit(response.body()!!) /// Need to find a work around for empty response
                    in 400..499 -> throw ClientRequestErrorResult(ApiError(response.code(), response.message()))
                    in 500..511 -> throw ServerResponseErrorResult(ServerError(response.code(), response.message()))
                    else -> throw UnknownApiError(123, "Unknown")
                }
            } catch (e: Exception) {
                /// convert exception to Error result for ui presentation
                throw when (e) {
                    is IOException,
                    is UnknownHostException,
                    is SocketTimeoutException -> NetworkUnavailable
                    /// Let the domain layer or Ui layer handle the unknown exceptions
                    else -> e
                    /// if the thrown exceptions are error result do not process it
                }

            }
        }

    companion object {
        fun <R> create(responseType: Type): FlowCallAdapter<R> {
            return FlowCallAdapter(responseType)
        }
    }
}
