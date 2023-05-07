package com.core.themes

import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Modifier


fun Modifier.thumbnailSize() = this.size(
    height = MaterialTheme.dimension().thumbnailHeight,
    width = MaterialTheme.dimension().thumbnailWidth
)
