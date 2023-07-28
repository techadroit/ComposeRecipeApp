package com.core.themes

import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.unit.dp

fun MaterialTheme.dimension() = Dimension

object Dimension {

    val paddingBody = 8.dp
    val paddingTitle = 12.dp
    val paddingSubtitle = 8.dp
    val paddingPara = 4.dp
    val paddingContent = 16.dp

    val thumbnailHeight = 180.dp
    val thumbnailWidth = 160.dp

    val cardHeight = 230.dp
    val cardHorizontalPadding = 12.dp
    val cardVerticalPadding = 8.dp

    val contentPadding = 80.dp

    val videoListGrid = GridCells.Fixed(2)
}
