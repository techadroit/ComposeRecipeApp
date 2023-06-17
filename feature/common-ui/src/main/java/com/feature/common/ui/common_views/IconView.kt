package com.feature.common.ui.common_views

import androidx.compose.foundation.clickable
import androidx.compose.material3.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun SaveIcon(modifier: Modifier = Modifier,isSaved: Boolean, onClick: (Boolean) -> Unit) {
    val saved = remember {
        mutableStateOf(isSaved)
    }
    if (saved.value)
        Icon(
            imageVector = Icons.Default.Favorite,
            contentDescription = "Saved",
            tint = Color.Red,
            modifier = modifier.clickable {
                saved.value = false
                onClick.invoke(saved.value)
            }
        )
    else
        Icon(
            imageVector = Icons.Default.FavoriteBorder,
            contentDescription = "Not Saved",
            modifier = modifier.clickable {
                saved.value = true
                onClick.invoke(saved.value)
            }
        )
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFF)
@Composable
fun SaveIconPreview(){
    SaveIcon(isSaved = true){

    }
}
