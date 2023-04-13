package com.example.composerecipeapp.platform.navigation.navigator

interface DestinationIntent {
    val screenName: String
    fun toRoute(): String? = null

    fun getRouteName() = toRoute() ?: screenName
}
