package com.feature.home.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import com.archerviewmodel.ArcherViewModel
import com.core.navigtion.AppNavigator
import com.core.platform.functional.ViewEffect
import com.core.themes.dimension
import com.core.themes.homeCard
import com.core.themes.homePadding
import com.domain.common.pojo.RecipeModel
import com.domain.recipe.cuisines.RecipeWithCuisine
import com.feature.common.Dispatch
import com.feature.common.OnClick
import com.feature.common.fullScreen
import com.feature.common.observeState
import com.feature.common.ui.common_views.LoadingView
import com.feature.common.ui.common_views.RefreshView
import com.feature.common.ui.recipes.ImageThumbnail
import com.feature.home.state.*
import com.feature.home.viewmodel.HomeRecipeViewModel
import com.feature.recipe.home.R
import com.recipe.app.navigation.intent.RecipeDetailIntent
import com.recipe.app.navigation.intent.RecipeListIntent
import com.recipe.app.navigation.provider.MainViewNavigator
import com.recipe.app.navigation.provider.ParentNavHostController

@Composable
fun HomeView() {

    val topLevelNavigator = ParentNavHostController.current
    val mainViewNavigator = MainViewNavigator.current
    val viewModel = hiltViewModel<HomeRecipeViewModel>()
    val state = viewModel.observeState()
    val viewEffect = state.viewEffect?.consume()

    fun refresh() {
        viewModel.dispatch(RefreshHomeEvent)
    }

    if (state.list.isNotEmpty()) {
        RefreshView(content = {
            HomeViewContent(list = state.list, viewModel = viewModel)
        }) {
            refresh()
        }
    } else if (state.isLoadingPage) {
        LoadingView()
    } else if (viewEffect is LoadingError) {
        Box(modifier = Modifier.fullScreen()) {
            OutlinedButton(
                onClick = {
                    refresh()
                },
                modifier = Modifier.align(Alignment.Center)
            ) {
                Text(
                    text = "Retry",
                    style = MaterialTheme.typography.displayLarge,
                    modifier = Modifier
                        .padding(8.dp)
                )
            }
        }
    }

    viewEffect?.let { onViewEffect(it, topLevelNavigator, mainViewNavigator) }
}

@Composable
fun HomeViewContent(
    list: List<RecipeWithCuisine>,
    viewModel: ArcherViewModel<HomeRecipeState, HomeRecipeEvent>
) {
    LazyColumn(
        content = {
            items(list) {
                Text(
                    text = it.cuisine,
                    style = MaterialTheme.typography.displayLarge,
                    modifier = Modifier.padding(8.dp)
                )
                RecipeListWithCuisine(recipe = it, viewModel = viewModel)
            }
        }
    )
}

@Composable
fun RecipeListWithCuisine(
    recipe: RecipeWithCuisine,
    viewModel: ArcherViewModel<HomeRecipeState, HomeRecipeEvent>
) {
    val scrollState = rememberLazyListState()
    LazyRow(
        state = scrollState,
        content = {
            itemsIndexed(recipe.recipeList) { index, recipe ->
                key(index) {
                    RecipeItem(
                        recipe = recipe
                    ) {
                        viewModel.dispatch(ViewRecipeDetail(recipeId = it.toString()))
                    }
                }
            }
            item {
                ViewAll {
                    viewModel.dispatch(ViewAllRecipes(recipe.cuisine))
                }
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeItem(recipe: RecipeModel, onRowClick: OnClick<Int>) {
    Box(
        modifier = Modifier.homePadding()
    ) {
        Card(
            onClick = { onRowClick(recipe.id) },
            modifier = Modifier
                .homeCard()
                .shadow(shape = RoundedCornerShape(16.dp), elevation = 0.dp)
        ) {
            ConstraintLayout {
                val (thumbnail, title) = createRefs()
                ImageThumbnail(
                    imageUrl = recipe.imageUrl,
                    modifier = Modifier.constrainAs(thumbnail) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    })
                Text(
                    text = recipe.title,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 2,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .constrainAs(title) {
                            top.linkTo(thumbnail.bottom)
                            start.linkTo(thumbnail.start)
                            end.linkTo(thumbnail.end)
                            bottom.linkTo(parent.bottom)
                            width = Dimension.fillToConstraints
                        }
                        .padding(
                            start = MaterialTheme.dimension().paddingSubtitle,
                            top = MaterialTheme.dimension().paddingSubtitle,
                            bottom = MaterialTheme.dimension().paddingSubtitle,
//                            bottom = 0.dp,
                        )
                )
            }
        }
    }
}

@Composable
fun ViewAll(dispatch: Dispatch<Unit>) {
    Card(
        modifier = Modifier
            .homeCard()
            .homePadding()
            .clickable { dispatch(Unit) }
    ) {
        Column(
            modifier = Modifier.fillMaxHeight(),
            verticalArrangement = Arrangement.Center) {
            Text(
                text = stringResource(id = R.string.view_all),
                style = MaterialTheme.typography.displayLarge,
                maxLines = 2,
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}

@Composable
fun onViewEffect(
    viewEffect: ViewEffect,
    parentNavigation: AppNavigator,
    appMainNavigation: AppNavigator
) {
    when (viewEffect) {
        is ViewAllViewEffect ->
            appMainNavigation.navigateTo(RecipeListIntent(cuisine = viewEffect.cuisine))

        is ViewRecipesDetailViewEffect ->
            parentNavigation.navigateTo(RecipeDetailIntent(detailId = viewEffect.recipeId))

        is LoadingError -> {
            println("there is an error")
        }
    }
}
