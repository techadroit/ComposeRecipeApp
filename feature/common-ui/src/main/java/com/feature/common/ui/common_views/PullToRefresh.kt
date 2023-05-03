package com.feature.common.ui.common_views

import androidx.compose.foundation.layout.Box
import com.feature.common.ui.pull_refresh.PullRefreshIndicator
import com.feature.common.ui.pull_refresh.pullRefresh
import com.feature.common.ui.pull_refresh.rememberPullRefreshState

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun RefreshView(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
    onRefresh: () -> Unit
) {
    val isRefreshing by remember {
        mutableStateOf(false)
    }
    val pullRefreshState = rememberPullRefreshState(refreshing = isRefreshing, onRefresh = {
        onRefresh()
    })
    Box(
        modifier.pullRefresh(pullRefreshState)
    ) {
        content()
        PullRefreshIndicator(
            refreshing = isRefreshing,
            state = pullRefreshState,
            modifier = Modifier.align(Alignment.TopCenter)
        )
    }
}
