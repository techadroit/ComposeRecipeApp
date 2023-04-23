package com.example.composerecipeapp.core.network.call_adapter

import kotlinx.coroutines.flow.Flow
import retrofit2.CallAdapter
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

class FlowCallAdapterFactory : CallAdapter.Factory() {
    override fun get(returnType: Type, annotations: Array<out Annotation>, retrofit: Retrofit): CallAdapter<*, *>? {
        if (getRawType(returnType) != Flow::class.java) {
            return null
        }

        if (returnType !is ParameterizedType) {
            throw IllegalArgumentException("CustomCall return type must be parameterized")
        }

        val responseType = getParameterUpperBound(0, returnType)
        return FlowCallAdapter.create<Any>(responseType)
    }
}
