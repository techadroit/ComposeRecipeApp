package com.example.composerecipeapp.ui.views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.composerecipeapp.ui.pojo.RecipeModel
import com.skydoves.landscapist.glide.GlideImage


@Composable
fun CookingTime(time: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            imageVector = Icons.Default.DateRange, contentDescription = "Cooking Time",
            tint = Color.LightGray, modifier = Modifier
                .width(14.dp)
                .height(14.dp)
        )
        Spacer(modifier = Modifier.width(2.dp))
        Text(text = "$time mint", style = MaterialTheme.typography.subtitle1)
    }
}

@Composable
fun Servings(serving: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            imageVector = Icons.Default.Person, contentDescription = "Servings",
            tint = Color.LightGray, modifier = Modifier
                .width(14.dp)
                .height(14.dp)
        )
        Spacer(modifier = Modifier.width(2.dp))
        Text(text = "$serving servings", style = MaterialTheme.typography.subtitle1)
    }
}
