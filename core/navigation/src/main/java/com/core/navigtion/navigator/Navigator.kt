package com.core.navigtion.navigator

import androidx.compose.runtime.Composable
import androidx.navigation.NavController

interface Navigator<T> {
    fun navigateTo(navItems: T, popUpTo: T? = null, inclusive: Boolean = false)
    @Composable
    fun getCurrentRoute(): String?
    fun getNavController(): NavController
}
