package com.example.composerecipeapp.ui.recipes

import android.content.Intent
import android.text.Html
import android.widget.TextView
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.ClickableText
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.composerecipeapp.ParentNavHostController
import com.example.composerecipeapp.ui.ComposeRecipeAppTheme
import com.example.composerecipeapp.ui.pojo.RecipeDetailModel
import com.example.composerecipeapp.ui.pojo.RecipeModel
import com.example.composerecipeapp.util.fullScreen
import com.example.composerecipeapp.ui.viewmodel.LoadRecipeDetail
import com.example.composerecipeapp.ui.viewmodel.RecipeDetailState
import com.example.composerecipeapp.ui.viewmodel.RecipeDetailViewModel
import com.skydoves.landscapist.glide.GlideImage


@Composable
fun RecipeDetail(recipeId: String, navController: NavController = ParentNavHostController.current) {

    val viewModel: RecipeDetailViewModel = hiltViewModel()
    val state = viewModel.stateEmitter.collectAsState().value

    Surface {
        if (state.isLoading)
            LoadingView()
        RecipeDetailBody(state = state, navController = navController)
    }

    LaunchedEffect(recipeId) {
        viewModel.dispatch(LoadRecipeDetail(recipeId))
    }
}

@Composable
fun RecipeDetailBody(state: RecipeDetailState, navController: NavController){
    Column(modifier = Modifier.fullScreen()) {
        state.recipeDetail?.let {
            RecipeDetailContentView(it, navController)
        }

//        state.similarRecipe?.let {
//            Text(modifier = Modifier.padding(12.dp),text = "Similar Recipes",style = MaterialTheme.typography.h1)
//            RecipeList(recipeList = it)
//        }
    }
}

@Composable
fun RecipeList(recipeList: List<RecipeModel>){
    LazyColumn(
        content = {
            itemsIndexed(recipeList){ index,recipe ->
                RecipeListItem(
                    recipe = recipe,
                    index = index,
                    navHostController = ParentNavHostController.current
                )
            }
        }
    )
}

@Composable
fun RecipeDetailContentView(recipeDetail: RecipeDetailModel, navController: NavController) {
    val context = LocalContext.current
    Column(modifier = Modifier.wrapContentHeight()) {
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
        AndroidView(modifier = Modifier.wrapContentHeight(),
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
