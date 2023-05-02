package com.recipe.app.navigation.provider

import android.annotation.SuppressLint
import androidx.compose.runtime.compositionLocalOf
import com.core.navigtion.AppNavigator

@SuppressLint("CompositionLocalNaming")
val ParentNavHostController = compositionLocalOf<AppNavigator> {
    error("No navigation controller found")
}

@SuppressLint("CompositionLocalNaming")
val MainViewNavigator = compositionLocalOf<AppNavigator> {
    error("No navigation controller found")
}
