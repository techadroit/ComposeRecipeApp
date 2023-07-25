package com.core.themes

import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun MaterialTheme.dimension() = LocalDimensionProvider.current

fun createDimension(
    paddingBody: Dp = 8.dp,
    paddingTitle: Dp = 12.dp,
    paddingSubtitle: Dp = 8.dp,
    paddingPara: Dp = 4.dp,
    thumbnailHeight: Dp = 180.dp,
    thumbnailWidth: Dp = 160.dp,
    cardHeight: Dp = 230.dp,
    cardHorizontalPadding: Dp = 12.dp,
    cardVerticalPadding: Dp = 8.dp,
    contentPadding: Dp = 80.dp,
    videoListGrid: GridCells = GridCells.Fixed(2),

    ) = Dimension().apply {
    this.paddingBody = paddingBody
    this.paddingTitle = paddingTitle
    this.paddingSubtitle = paddingSubtitle
    this.thumbnailHeight = thumbnailHeight
    this.thumbnailWidth = thumbnailWidth
    this.cardHeight = cardHeight
    this.cardHorizontalPadding = cardHorizontalPadding
    this.paddingPara = paddingPara
    this.cardVerticalPadding = cardVerticalPadding
    this.contentPadding = contentPadding
    this.videoListGrid = videoListGrid
}

fun compactDimension() = createDimension()
fun mediumDimension() = createDimension()
fun expandedDimension() = createDimension()

class Dimension {
    var spacingXs = 4.dp
    var spacingSmall = 8.dp
    var spacingMedium = 12.dp
    var spacingLarge = 16.dp
    var spacingXLarge = 32.dp
    var paddingBody = 8.dp
    var paddingTitle = 12.dp
    var paddingSubtitle = 8.dp
    var paddingPara = 4.dp
    var thumbnailHeight = 180.dp
    var thumbnailWidth = 160.dp
    var cardHeight = 230.dp
    var cardHorizontalPadding = 12.dp
    var cardVerticalPadding = 8.dp
    var contentPadding = 80.dp
    var videoListGrid: GridCells = GridCells.Fixed(2)
    /// used to assign max width for a content in tab mode
    var maxContentWidth: Dp = 400.dp
}

val LocalDimensionProvider = compositionLocalOf { Dimension() }