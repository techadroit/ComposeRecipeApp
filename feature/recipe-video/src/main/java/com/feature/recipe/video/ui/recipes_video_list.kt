package com.feature.recipe.video.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.core.themes.dimension
import com.core.themes.iconHeightMedium
import com.core.themes.spacerSmall
import com.domain.common.pojo.VideoRecipeModel
import com.feature.common.Dispatch
import com.feature.common.Navigate
import com.feature.common.collectState
import com.feature.common.observeSideEffect
import com.feature.common.observeState
import com.feature.common.toViews
import com.feature.common.ui.common_views.LoadingView
import com.feature.common.ui.common_views.PaginationLoading
import com.feature.common.ui.common_views.RefreshView
import com.feature.common.ui.error_screen.ErrorScreen
import com.feature.common.ui.error_screen.ErrorSideEffect
import com.feature.recipe.video.state.LoadVideos
import com.feature.recipe.video.state.RefreshVideoScreen
import com.feature.recipe.video.state.VideoEvents
import com.feature.recipe.video.viewmodel.VideoListViewModel
import com.recipe.app.navigation.intent.VideoPlayerIntent
import com.recipe.app.navigation.provider.ParentNavHostController
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun RecipesVideoList( recipesViewModel: VideoListViewModel = hiltViewModel()) {

    val topLevelNavigator = ParentNavHostController.current

    recipesViewModel.collectState { recipeState ->
        RefreshView(content = {
            RecipeListContent(recipeState.data,
                recipeState.isLoading && recipeState.isPaginate, {
                    recipesViewModel.dispatch(it)
                }, {
                    topLevelNavigator.navigateTo(it)
                })
        }) {
            recipesViewModel.dispatch(RefreshVideoScreen)
        }

        if (recipeState.isLoading && !recipeState.isPaginate) {
            LoadingView()
        }

        recipeState.failure?.let {
            ErrorScreen(errorResult = it) {
                recipesViewModel.dispatch(RefreshVideoScreen)
            }
        }
    }
}

@Composable
fun RecipeListContent(
    list: List<VideoRecipeModel>,
    showPaginationLoading: Boolean,
    dispatch: Dispatch<VideoEvents>,
    navigate: Navigate
) {
    val scrollState = rememberLazyGridState()

    LazyVerticalGrid(
        columns = MaterialTheme.dimension().videoListGrid,
        state = scrollState,
        contentPadding = PaddingValues(bottom = MaterialTheme.dimension().contentPadding),
        content = {
            itemsIndexed(list) { index, recipe ->
                VideoCard(index = index, recipe = recipe, navigate)
                val totalItem = scrollState.layoutInfo.totalItemsCount
                if (index == (totalItem - 1)) {
                    LaunchedEffect(true) {
                        dispatch(LoadVideos(isPaginate = true))
                    }
                }
            }
            if (showPaginationLoading) {
                item {
                    PaginationLoading()
                }
            }
        }
    )
}

@Composable
fun VideoCard(index: Int, recipe: VideoRecipeModel, navigate: Navigate) {
    Card(
        modifier = Modifier
            .wrapContentHeight()
            .padding(
                horizontal = 12.dp,
                vertical = if (index == 0) 8.dp else 4.dp
            )
            .clickable {
                navigate(VideoPlayerIntent(recipe.youTubeId))
            }
    ) {
        VideoContent(recipe = recipe)
    }
}

@Composable
fun VideoContent(recipe: VideoRecipeModel) {
    Box(Modifier.wrapContentSize()) {
        Column {
            Thumbnail(url = recipe.thumbnail)
            Column(modifier = Modifier.padding(8.dp)) {
                Text(text = recipe.shortTitle, style = MaterialTheme.typography.bodyMedium)
                Spacer(modifier = Modifier.spacerSmall())
                Views(views = recipe.views)
            }
        }
    }
}

@Composable
fun Thumbnail(url: String) {
    Box(modifier = Modifier.fillMaxSize()) {
        GlideImage(
            imageModel = url,
            contentScale = ContentScale.FillHeight,
            modifier = Modifier
                .fillMaxWidth()
                .height(MaterialTheme.dimension().thumbnailHeight),
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(MaterialTheme.dimension().thumbnailHeight)
                .background(Color(0xB3000000))
        ) {
            Icon(
                imageVector = Icons.Default.PlayArrow, contentDescription = "play",
                modifier = Modifier
                    .iconHeightMedium()
                    .align(Alignment.Center),
                tint = Color.LightGray
            )
        }
    }
}

@Composable
fun Views(views: Int) {
    val convertedViews = toViews(views.toLong())
    Text(text = "$convertedViews views", style = MaterialTheme.typography.titleMedium)
}
