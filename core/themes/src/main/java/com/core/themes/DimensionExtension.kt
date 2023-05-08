package com.core.themes

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Modifier


fun Modifier.thumbnailSize() = size(
    height = MaterialTheme.dimension().thumbnailHeight,
    width = MaterialTheme.dimension().thumbnailWidth
)

fun Modifier.homeCard() =
    height(MaterialTheme.dimension().cardHeight)
    .padding(
        horizontal = MaterialTheme.dimension().cardHorizontalPadding,
        vertical = MaterialTheme.dimension().cardVerticalPadding
    )
