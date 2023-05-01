package com.core.navigtion.intent

interface DestinationIntent {
    val screenName: String
    fun toRoute(): String? = null
    fun getRouteName() = toRoute() ?: screenName
}
