package com.example.composerecipeapp.ui.views

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

@Composable
fun SaveIcon(isSaved: Boolean, onClick: (Boolean) -> Unit) {
    val saved = remember {
        mutableStateOf(isSaved)
    }
    if (saved.value)
        Icon(
            imageVector = Icons.Default.Favorite,
            contentDescription = "Saved",
            tint = Color.Red,
            modifier = Modifier.clickable {
                saved.value = false
                onClick.invoke(saved.value)
            }
        )
    else
        Icon(
            imageVector = Icons.Default.FavoriteBorder,
            contentDescription = "Not Saved",
            modifier = Modifier.clickable {
                saved.value = true
                onClick.invoke(saved.value)
            }
        )
}
