package com.example.composerecipeapp.ui.recipes

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.composerecipeapp.core.Resource
import com.example.composerecipeapp.ui.pojo.VideoRecipeModel
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun recipesVideoList() {

    val recipesViewmodel: ViewmodelForVideoRecipes =
        viewModel(modelClass = ViewmodelForVideoRecipes::class.java)
    recipesViewmodel.loadVideoRecipes()
    val recipeState = recipesViewmodel.stateFlow.collectAsState().value.data
    when (recipeState) {
        is Resource.Success -> {
            val recipeList = recipeState.data as List<VideoRecipeModel>
            LazyColumn(content = {
                itemsIndexed(recipeList) { index, recipe ->
                   videoCard(index = index, recipe = recipe)
                }
            })
        }
        else -> {
            renderText(message = "loading")
        }
    }
}

@Composable
fun videoCard(index: Int,recipe: VideoRecipeModel){
    Card(
        modifier = Modifier
            .wrapContentHeight()
            .padding(
                horizontal = 12.dp,
                vertical = if (index == 0) 8.dp else 4.dp
            )
    ) {
        videoContent(recipe = recipe)
    }
}

@Composable
fun videoContent(recipe: VideoRecipeModel) {
    Column {
        GlideImage(
            imageModel = recipe.thumbnail,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
        )
        Column(modifier = Modifier.padding(8.dp)) {
            Text(text = recipe.shortTitle,color=Color.Black)
            Text(text = recipe.rating.toString(),color = Color.Black)
        }

    }
}

@Preview
@Composable
fun previewVideoCard(){
    videoCard(
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
