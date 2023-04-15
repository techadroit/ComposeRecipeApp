package com.example.composerecipeapp.ui.provider

import android.annotation.SuppressLint
import androidx.compose.runtime.compositionLocalOf
import androidx.navigation.NavHostController
import com.example.composerecipeapp.platform.navigation.navigator.AppMainNavigation

@SuppressLint("CompositionLocalNaming")
val ParentNavHostController = compositionLocalOf<AppMainNavigation> {
    error("No navigation controller found")
}

@SuppressLint("CompositionLocalNaming")
val MainViewNavigator = compositionLocalOf<AppMainNavigation> {
    error("No navigation controller found")
}
