package com.core.navigtion

interface DestinationIntent {
    val screenName: String
    fun toRoute(): String? = null
    fun getRouteName() = toRoute() ?: screenName
}
