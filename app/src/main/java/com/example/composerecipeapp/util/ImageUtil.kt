package com.example.composerecipeapp.util

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import com.skydoves.landscapist.glide.GlideImage
import kotlinx.coroutines.ExperimentalCoroutinesApi

@Composable
fun loadPicture(url: String, @DrawableRes defaultImage: Int?) {

//    val bitmapState: MutableState<Bitmap?> = mutableStateOf(null)

    // show default image while image loads
//    Glide.with(AmbientContext.current)
//        .asBitmap()
//        .load(defaultImage)
//        .into(object : CustomTarget<Bitmap>() {
//            override fun onLoadCleared(placeholder: Drawable?) {}
//            override fun onResourceReady(
//                resource: Bitmap,
//                transition: com.bumptech.glide.request.transition.Transition<in Bitmap>?
//            ) {
//                bitmapState.value = resource
//            }
//        })


    GlideImage(
        imageModel = url
    )

//    // get network image
//    Glide.with(AmbientContext.current)
//        .asBitmap()
//        .load(url)
//        .into(object : CustomTarget<Bitmap>() {
//            override fun onLoadCleared(placeholder: Drawable?) {}
//            override fun onResourceReady(
//                resource: Bitmap,
//                transition: com.bumptech.glide.request.transition.Transition<in Bitmap>?
//            ) {
//                bitmapState.value = resource
//            }
//        })

//    return bitmapState
}
