package com.example.composerecipeapp.ui.provider

import android.annotation.SuppressLint
import androidx.compose.runtime.compositionLocalOf
import androidx.navigation.NavHostController

@SuppressLint("CompositionLocalNaming")
val ParentNavHostController = compositionLocalOf<NavHostController> {
    error("No navigation controller found")
}
