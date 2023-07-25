package com.core.themes

import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.compositionLocalOf
import androidx.window.layout.DisplayFeature

@ReadOnlyComposable
fun getAdaptiveSizes(windowSizeClass: WindowSizeClass): AdaptiveSizes{

    val windowSize = when(windowSizeClass.widthSizeClass){
        WindowWidthSizeClass.Compact -> WindowsSize.COMPACT
        WindowWidthSizeClass.Medium -> WindowsSize.MEDIUM
        WindowWidthSizeClass.Expanded -> WindowsSize.EXPANDED
        else -> WindowsSize.LARGE
    }

    return AdaptiveSizes().apply {
        this.windowSize = windowSize
    }
}

class AdaptiveSizes {
    var windowSize = WindowsSize.COMPACT
}

enum class WindowsSize{
    COMPACT,
    MEDIUM,
    EXPANDED,
    LARGE
}

val LocalAdaptiveSizeProvider = compositionLocalOf { AdaptiveSizes() }