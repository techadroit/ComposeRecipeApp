package com.example.composerecipeapp.ui.recipe_videos

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
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
import com.domain.common.pojo.VideoRecipeModel
import com.example.composerecipeapp.ui.util.Dispatch
import com.example.composerecipeapp.ui.util.Navigate
import com.example.composerecipeapp.ui.views.LoadingView
import com.example.composerecipeapp.ui.views.PaginationLoading
import com.example.composerecipeapp.ui.views.RefreshView
import com.example.composerecipeapp.util.observeState
import com.example.composerecipeapp.util.toViews
import com.example.composerecipeapp.viewmodel.recipe_video.LoadVideos
import com.example.composerecipeapp.viewmodel.recipe_video.RefreshVideoScreen
import com.example.composerecipeapp.viewmodel.recipe_video.VideoEvents
import com.example.composerecipeapp.viewmodel.recipe_video.VideoListViewmodel
import com.recipe.app.navigation.intent.VideoPlayerIntent
import com.recipe.app.navigation.provider.ParentNavHostController
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun RecipesVideoList() {

    val topLevelNavigator = ParentNavHostController.current
    val recipesViewModel: VideoListViewmodel = hiltViewModel()
    val recipeState = recipesViewModel.observeState()

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
        recipesViewModel.dispatch(LoadVideos())
    }
}

@Composable
fun RecipeListContent(
    list: List<VideoRecipeModel>,
    showPaginationLoading: Boolean,
    dispatch: Dispatch<VideoEvents>,
    navigate: Navigate
) {
    val scrollState = rememberLazyListState()
    LazyColumn(
        state = scrollState,
        contentPadding = PaddingValues(bottom = 80.dp),
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
                Text(text = recipe.shortTitle, style = MaterialTheme.typography.displayLarge)
                Spacer(modifier = Modifier.height(2.dp))
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
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .background(Color(0xB3000000))
        ) {
            Icon(
                imageVector = Icons.Default.PlayArrow, contentDescription = "play",
                modifier = Modifier
                    .height(80.dp)
                    .width(80.dp)
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
