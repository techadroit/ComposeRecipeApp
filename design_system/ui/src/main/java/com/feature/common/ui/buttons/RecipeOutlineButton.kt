package com.core.themes.buttons

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

object RecipeOutlineButton {

    @Composable
    fun ErrorButton(text: String, modifier: Modifier, onClick: () -> Unit) {
        OutlinedButton(
            onClick = {
                onClick()
            }, modifier = modifier
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.displayLarge,
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}