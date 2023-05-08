package com.core.themes

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


fun Modifier.thumbnailSize() = size(
    height = MaterialTheme.dimension().thumbnailHeight,
    width = MaterialTheme.dimension().thumbnailWidth
)

fun Modifier.homeCard() =
    height(MaterialTheme.dimension().cardHeight)

fun Modifier.homePadding() = padding(
    start = MaterialTheme.dimension().cardHorizontalPadding,
    end = 0.dp,
    top = MaterialTheme.dimension().cardVerticalPadding,
    bottom = MaterialTheme.dimension().cardVerticalPadding,
)

fun Modifier.spacerSmall() = height(2.dp)

fun Modifier.iconHeightMedium() = height(40.dp).width(40.dp)
