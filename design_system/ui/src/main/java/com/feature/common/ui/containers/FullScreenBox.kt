package com.feature.common.ui.containers

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.feature.common.ui.extension.fullScreen

@Composable
fun FullScreenBox(content: @Composable BoxScope.() -> Unit) {
    Box(modifier = Modifier.fullScreen()) {
        content()
    }
}