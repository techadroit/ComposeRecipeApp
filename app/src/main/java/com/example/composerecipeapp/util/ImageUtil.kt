package com.example.composerecipeapp.util

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun loadPicture(url: String, @DrawableRes defaultImage: Int?) {
    GlideImage(
        imageModel = url
    )
}
