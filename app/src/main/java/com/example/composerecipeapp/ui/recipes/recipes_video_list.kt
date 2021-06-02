package com.example.composerecipeapp.ui.recipes

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.navigate
import com.example.composerecipeapp.ParentNavHostController
import com.example.composerecipeapp.ui.pojo.VideoRecipeModel
import com.example.composerecipeapp.util.toViews
import com.recipeapp.view.viewmodel.LoadVideos
import com.recipeapp.view.viewmodel.VideoListViewmodel
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun RecipesVideoList(navController: NavController= ParentNavHostController.current) {

    val recipesViewModel: VideoListViewmodel =
        viewModel(modelClass = VideoListViewmodel::class.java)
    LaunchedEffect(Unit) {
        recipesViewModel.dispatch(LoadVideos())
    }
    val recipeState = recipesViewModel.stateEmitter.collectAsState().value

    RecipeListContent(
        recipesViewModel,
        recipeState.data,
        recipeState.isLoading && recipeState.isPaginate,
        navController
    )
    if (recipeState.isLoading && !recipeState.isPaginate)
        LoadingView()
}

@Composable
fun RecipeListContent(
    recipesViewModel: VideoListViewmodel,
    list: List<VideoRecipeModel>,
    showPaginationLoading: Boolean,
    navHostController: NavController
) {
    val scrollState = rememberLazyListState()
    LazyColumn(
        state = scrollState,
        contentPadding = PaddingValues(bottom = 80.dp),
        content = {
            itemsIndexed(list) { index, recipe ->
                VideoCard(index = index, recipe = recipe, navHostController)
                val totalItem = scrollState.layoutInfo.totalItemsCount
                if (index == (totalItem - 1)) {
                    LaunchedEffect(true) {
                        recipesViewModel.dispatch(LoadVideos(isPaginate = true))
                    }
                }
            }
            if (showPaginationLoading) {
                item {
                    PaginationLoading()
                }
            }
        })
}

@Composable
fun VideoCard(index: Int, recipe: VideoRecipeModel, navHostController: NavController) {
    Card(
        modifier = Modifier
            .wrapContentHeight()
            .padding(
                horizontal = 12.dp,
                vertical = if (index == 0) 8.dp else 4.dp
            )
            .clickable {
                navHostController.navigate("recipe/videos/${recipe.youTubeId}")
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
                Text(text = recipe.shortTitle, style = MaterialTheme.typography.h1)
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
    Text(text = "$convertedViews views", style = MaterialTheme.typography.subtitle1)
}
