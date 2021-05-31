package com.example.composerecipeapp.ui.recipes

import android.content.Intent
import android.text.Html
import android.widget.TextView
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.net.toUri
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.composerecipeapp.ui.ComposeRecipeAppTheme
import com.example.composerecipeapp.ui.pojo.RecipeDetailModel
import com.example.composerecipeapp.util.fullScreen
import com.recipeapp.view.viewmodel.LoadRecipeDetail
import com.recipeapp.view.viewmodel.RecipeDetailViewModel
import com.skydoves.landscapist.glide.GlideImage


@Composable
fun RecipeDetail(recipeId: String, navController: NavController) {

    val viewModel: RecipeDetailViewModel = viewModel(modelClass = RecipeDetailViewModel::class.java)
    val state = viewModel.stateEmitter.collectAsState().value

    Surface {
        if (state.isLoading)
            LoadingView()
        state.recipeDetail?.let {
            RecipeDetailContentView(it, navController)
        }
    }

    LaunchedEffect(recipeId) {
        viewModel.dispatch(LoadRecipeDetail(recipeId))
    }
}

@Composable
fun RecipeDetailContentView(recipeDetail: RecipeDetailModel, navController: NavController) {
    val context = LocalContext.current
    Column(modifier = Modifier.fullScreen()) {
        GlideImage(
            imageModel = recipeDetail.imageUrl,
            modifier = Modifier
                .fillMaxWidth()
                .height(240.dp)
        )
        RecipeDescription(recipeDetail = recipeDetail) {
            val uri = it.toUri()
            context.startActivity(Intent(Intent.ACTION_VIEW, uri))
        }

        RecipeContent(recipeDetail = recipeDetail)
    }
}

@Composable
fun RecipeContent(recipeDetail: RecipeDetailModel) {
    Column(modifier = Modifier.padding(12.dp)) {
        AndroidView(modifier = Modifier.fullScreen(),
            factory = { context ->
                TextView(context).apply {
                    this.text = Html.fromHtml(recipeDetail.instructions)
                }
            }) {

        }
    }
}

@Composable
fun RecipeDescription(recipeDetail: RecipeDetailModel, onSourceClick: (url: String) -> Unit) {
    Column(
        Modifier
            .padding(12.dp)
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        Text(text = recipeDetail.title,style = MaterialTheme.typography.h1)
        Spacer(modifier = Modifier.height(4.dp))
        ClickableText(text = AnnotatedString(recipeDetail.sourceName)) {
            onSourceClick.invoke(recipeDetail.sourceUrl)
        }
    }
}

class RecipeDetailProvider
    : PreviewParameterProvider<RecipeDetailModel> {
    override val values: Sequence<RecipeDetailModel>
        get() = sequenceOf(
            RecipeDetailModel(
                title = "Chicken Tandoori",
                sourceName = "Spoonacular",
                sourceUrl = "",
                instructions = "many instrucdtions",
                imageUrl = ""
            )
        )

    override val count: Int
        get() = values.count()
}

@Preview(backgroundColor = 0xFFFFFFFF,showBackground = true)
@Composable
fun RecipeDescription1(
    @PreviewParameter(RecipeDetailProvider::class)
    recipeDetail: RecipeDetailModel
) {
    ComposeRecipeAppTheme(darkTheme = true) {
        RecipeDescription(recipeDetail) {}
    }
}
