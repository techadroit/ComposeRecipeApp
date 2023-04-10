package com.example.composerecipeapp.core.network

interface NetworkServiceProvider {
    fun <T> getService(service: Class<T>) : T
}
