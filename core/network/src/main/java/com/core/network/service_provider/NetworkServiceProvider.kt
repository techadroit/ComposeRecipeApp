package com.core.network.service_provider

interface NetworkServiceProvider {
    fun <T> getService(service: Class<T>) : T
}
