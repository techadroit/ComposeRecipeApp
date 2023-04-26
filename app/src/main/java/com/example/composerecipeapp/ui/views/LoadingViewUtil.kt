package com.example.composerecipeapp.ui.views

import androidx.compose.foundation.layout.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp

@Composable
fun LoadingView() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator(
            modifier = Modifier
                .height(40.dp)
                .width(40.dp),
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}

@Composable
fun PaginationLoading() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .testTag("pagination_loading"),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator(
            modifier = Modifier
                .height(40.dp)
                .width(40.dp),
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}
