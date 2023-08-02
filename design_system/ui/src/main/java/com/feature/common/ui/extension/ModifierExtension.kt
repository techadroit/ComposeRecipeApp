package com.feature.common.ui.extension

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import com.core.themes.LocalAdaptiveSizeProvider
import com.core.themes.WindowsSize
import com.core.themes.dimension

fun Modifier.fullScreen() = this
    .fillMaxWidth()
    .fillMaxHeight()

fun Modifier.contentWidth(): Modifier = composed { run {
    val adaptiveSizes = LocalAdaptiveSizeProvider.current
    val dimension = MaterialTheme.dimension()
    if (adaptiveSizes.windowSize == WindowsSize.COMPACT) {
        fillMaxWidth()
    } else {
        width(dimension.maxContentWidth)
    }
} }