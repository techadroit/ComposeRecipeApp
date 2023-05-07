package com.feature.common.ui.recipes

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.core.themes.thumbnailSize
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun ImageThumbnail(imageUrl: String, modifier: Modifier = Modifier) {
    GlideImage(
        imageModel = imageUrl,
        modifier = modifier.thumbnailSize()
    )
}
