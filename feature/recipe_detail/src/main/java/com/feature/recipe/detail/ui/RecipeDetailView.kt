package com.feature.recipe.detail.ui

import android.content.Intent
import android.text.Html
import android.widget.TextView
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
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
import androidx.navigation.NavGraphBuilder
import com.core.navigtion.navigator.NavComposable
import com.core.themes.ComposeRecipeAppTheme
import com.core.themes.dimension
import com.domain.common.pojo.RecipeDetailModel
import com.feature.common.Dispatch
import com.feature.common.fullScreen
import com.feature.common.observeState
import com.feature.common.ui.common_views.LoadingView
import com.feature.common.ui.common_views.SaveIcon
import com.feature.recipe.detail.state.LoadRecipeDetail
import com.feature.recipe.detail.state.RecipeDetailState
import com.feature.recipe.detail.state.RemoveRecipe
import com.feature.recipe.detail.state.SaveRecipe
import com.feature.recipe.detail.viewmodel.RecipeDetailViewModel
import com.recipe.app.navigation.intent.RecipeDetailIntent
import com.skydoves.landscapist.glide.GlideImage

fun NavGraphBuilder.RecipeDetailScreen() {
    NavComposable(RecipeDetailIntent()) {
        val arguments = RecipeDetailIntent.getArguments(it.arguments)
        RecipeDetail(recipeId = arguments.recipeId)
    }
}

@Composable
fun RecipeDetail(recipeId: String) {

    val viewModel: RecipeDetailViewModel = hiltViewModel()
    val state = viewModel.observeState()

    Surface {
        if (state.isLoading)
            LoadingView()
        RecipeDetailBody(state = state, viewModel = viewModel)
    }

    LaunchedEffect(recipeId) {
        viewModel.dispatch(LoadRecipeDetail(recipeId))
    }
}

@Composable
fun RecipeDetailBody(
    state: RecipeDetailState,
    viewModel: com.feature.recipe.detail.viewmodel.RecipeDetailViewModel
) {
    Box {
        state.recipeDetail?.let {
            Column(modifier = Modifier.fullScreen()) {
                RecipeDetailContentView(it) { isSaved ->
                    viewModel.dispatch(if (isSaved) SaveRecipe else RemoveRecipe)
                }
            }
            SaveIcon(
                isSaved = it.isSaved,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(MaterialTheme.dimension().paddingBody)
            ) { isSaved ->
                viewModel.dispatch(if (isSaved) SaveRecipe else RemoveRecipe)
            }
        }
    }
}

@Composable
fun RecipeDetailContentView(recipeDetail: RecipeDetailModel, dispatch: Dispatch<Boolean>) {
    val context = LocalContext.current
    Column(modifier = Modifier.wrapContentHeight()) {
        GlideImage(
            imageModel = recipeDetail.imageUrl,
            modifier = Modifier
                .fillMaxWidth()
                .height(240.dp)
        )
        RecipeDescription(
            recipeDetail = recipeDetail
        ) {
            val uri = it.toUri()
            context.startActivity(Intent(Intent.ACTION_VIEW, uri))
        }
        RecipeContent(recipeDetail = recipeDetail)
    }
}

@Composable
fun RecipeContent(recipeDetail: RecipeDetailModel) {
    val scrollState = rememberScrollState(0)
    Column(modifier = Modifier.padding(12.dp)) {
        AndroidView(
            modifier = Modifier
                .wrapContentHeight()
                .verticalScroll(scrollState),
            factory = { context ->
                TextView(context).apply {
                    this.text = Html.fromHtml(recipeDetail.instructions)
                }
            }
        ) {
        }
    }
}

@Composable
fun RecipeDescription(
    recipeDetail: RecipeDetailModel,
    onSourceClick: (url: String) -> Unit,
) {
    Column(
        Modifier
            .padding(12.dp)
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        Text(text = recipeDetail.title, style = MaterialTheme.typography.displayLarge)
        Spacer(modifier = Modifier.height(4.dp))
        ClickableText(text = AnnotatedString(recipeDetail.sourceName)) {
            onSourceClick.invoke(recipeDetail.sourceUrl)
        }
    }
}

class RecipeDetailProvider :
    PreviewParameterProvider<RecipeDetailModel> {
    override val values: Sequence<RecipeDetailModel>
        get() = sequenceOf(
            RecipeDetailModel(
                title = "Chicken Tandoori",
                sourceName = "Spoonacular",
                sourceUrl = "",
                instructions = "many instrucdtions",
                imageUrl = "",
                cookingTime = 0,
                servings = 0,
                isSaved = false,
                id = 0
            )
        )

    override val count: Int
        get() = values.count()
}

@Preview(backgroundColor = 0xFFFFFFFF, showBackground = true)
@Composable
fun RecipeDescription1(
    @PreviewParameter(RecipeDetailProvider::class)
    recipeDetail: RecipeDetailModel
) {
    ComposeRecipeAppTheme(darkTheme = true) {
        RecipeDescription(recipeDetail, {})
    }
}
