package com.feature.common.ui.common_views

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun CookingTime(time: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            imageVector = Icons.Default.DateRange, contentDescription = "Cooking Time",
            tint = Color.LightGray,
            modifier = Modifier
                .width(14.dp)
                .height(14.dp)
        )
        Spacer(modifier = Modifier.width(2.dp))
        Text(text = "$time mint", style = MaterialTheme.typography.titleMedium)
    }
}

@Composable
fun Servings(serving: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            imageVector = Icons.Default.Person, contentDescription = "Servings",
            tint = Color.LightGray,
            modifier = Modifier
                .width(14.dp)
                .height(14.dp)
        )
        Spacer(modifier = Modifier.width(2.dp))
        Text(text = "$serving servings", style = MaterialTheme.typography.titleMedium)
    }
}
