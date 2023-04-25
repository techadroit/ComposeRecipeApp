package com.core.network.call_adapter

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import retrofit2.Call
import retrofit2.CallAdapter
import java.lang.reflect.Type

class FlowCallAdapter<T>(private val responseType: Type) : CallAdapter<T, Flow<T>> {
    override fun responseType(): Type {
        return responseType
    }

    override fun adapt(call: Call<T>): Flow<T> {
        val response = call.execute()
        return if (response.isSuccessful) {
            flowOf(response.body()!!)
        } else {
            throw Exception("HTTP Error Code: ${response.code()}")
        }
    }

    companion object {
        fun <R> create(responseType: Type): FlowCallAdapter<R> {
            return FlowCallAdapter(responseType)
        }
    }
}
