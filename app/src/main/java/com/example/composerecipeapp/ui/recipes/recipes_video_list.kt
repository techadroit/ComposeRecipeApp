package com.example.composerecipeapp.ui.recipes

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.composerecipeapp.ui.pojo.VideoRecipeModel
import com.recipeapp.view.viewmodel.LoadRecipes
import com.recipeapp.view.viewmodel.LoadVideos
import com.recipeapp.view.viewmodel.VideoListViewmodel
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun RecipesVideoList() {

    val recipesViewModel: VideoListViewmodel =
        viewModel(modelClass = VideoListViewmodel::class.java)
    LaunchedEffect(Unit) {
        recipesViewModel.add(LoadVideos())
    }
    val recipeState = recipesViewModel.stateEmitter.collectAsState().value

    RecipeListContent(recipesViewModel,recipeState.data)
    if (recipeState.isLoading && !recipeState.isPaginate)
        LoadingView()
}

@Composable
fun RecipeListContent(recipesViewModel : VideoListViewmodel,list: List<VideoRecipeModel>) {
    val scrollState = rememberLazyListState()
    LazyColumn(
        state = scrollState,
        content = {
        itemsIndexed(list) { index, recipe ->
            VideoCard(index = index, recipe = recipe)
            val totalItem = scrollState.layoutInfo.totalItemsCount
            if (index == (totalItem - 1)) {
                LaunchedEffect(true) {
                    recipesViewModel.add(LoadVideos(isPaginate = true))
                }
            }
        }
    })
}

@Composable
fun VideoCard(index: Int, recipe: VideoRecipeModel) {
    Card(
        modifier = Modifier
            .wrapContentHeight()
            .padding(
                horizontal = 12.dp,
                vertical = if (index == 0) 8.dp else 4.dp
            )
    ) {
        VideoContent(recipe = recipe)
    }
}

@Composable
fun VideoContent(recipe: VideoRecipeModel) {
    Column {
        GlideImage(
            imageModel = recipe.thumbnail,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
        )
        Column(modifier = Modifier.padding(8.dp)) {
            Text(text = recipe.shortTitle, color = Color.Black)
            Text(text = recipe.rating.toString(), color = Color.Black)
        }

    }
}

@Preview
@Composable
fun previewVideoCard() {
    VideoCard(
        index = 1,
        recipe = VideoRecipeModel(
            shortTitle = "hello",
            rating = 5.0,
            thumbnail = "https://i.ytimg.com/vi/Y0UBAhAS-wE/mqdefault.jpg",
            title = "hello title",
            views = 500,
            youTubeId = "sjlfjd"
        )
    )
}
