package com.example.composerecipeapp.ui.recipes

import android.text.Html
import android.widget.TextView
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.VerticalAlignmentLine
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.widget.TextViewCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.composerecipeapp.util.fullScreen
import com.example.composerecipeapp.data.network.response.RecipeDetailResponse
import com.example.composerecipeapp.ui.ComposeRecipeAppTheme
import com.recipeapp.view.viewmodel.LoadRecipeDetail
import com.recipeapp.view.viewmodel.RecipeDetailViewModel
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun RecipeDetail(recipeId: String){

    val viewModel : RecipeDetailViewModel = viewModel(modelClass = RecipeDetailViewModel::class.java)
    val state = viewModel.stateEmitter.collectAsState().value

    if(state.isLoading)
        LoadingView()
    state.recipeDetail?.let {
        RecipeDetailContentView(recipeDetail = it)
    }

    LaunchedEffect(recipeId){
        viewModel.add(LoadRecipeDetail(recipeId))
    }
}

@Composable
fun RecipeDetailContentView(recipeDetail: RecipeDetailResponse){
    Column(modifier = Modifier.fullScreen()) {
        GlideImage(imageModel = recipeDetail.image,
        modifier = Modifier
            .fillMaxWidth()
            .height(240.dp))
        RecipeDescription(recipeDetail = recipeDetail)
        RecipeContent(recipeDetail = recipeDetail)
    }
}

@Composable
fun RecipeContent(recipeDetail: RecipeDetailResponse){
    Column(modifier = Modifier.padding(12.dp)) {
        AndroidView(modifier = Modifier.fullScreen(),
        factory = {context -> TextView(context).apply {
            this.text = Html.fromHtml(recipeDetail.instructions)
        } }) {

        }
    }
}

@Composable
fun RecipeDescription(recipeDetail: RecipeDetailResponse){
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically){
        Text(text = recipeDetail.title)
        Button(onClick = {
            //TODO open website
        }) {
            Text(text = recipeDetail.sourceName)
        }
    }
}

@Preview
@Composable
fun RecipeDescription1(){
    ComposeRecipeAppTheme(darkTheme = false) {
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically){
            Text(text = "title")
            Button(onClick = {
                //TODO open website
            }) {
                Text(text = "sourceName")
            }
        }
    }
}
