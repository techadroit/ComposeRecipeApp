package com.example.composerecipeapp.ui.home_view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.archerviewmodel.ArcherViewModel
import com.example.composerecipeapp.R
import com.example.composerecipeapp.core.functional.ViewEffect
import com.example.composerecipeapp.domain.usecases.RecipeWithCuisine
import com.example.composerecipeapp.platform.navigation.navigator.AppMainNavigation
import com.example.composerecipeapp.platform.navigation.screens.RecipeDetailIntent
import com.example.composerecipeapp.platform.navigation.screens.RecipeListIntent
import com.example.composerecipeapp.ui.Dispatch
import com.example.composerecipeapp.ui.OnClick
import com.example.composerecipeapp.ui.pojo.RecipeModel
import com.example.composerecipeapp.ui.provider.MainViewNavigator
import com.example.composerecipeapp.ui.provider.ParentNavHostController
import com.example.composerecipeapp.ui.views.LoadingView
import com.example.composerecipeapp.util.observeState
import com.example.composerecipeapp.viewmodel.home_recipes.*
import com.skydoves.landscapist.glide.GlideImage

@ExperimentalMaterialApi
@Composable
fun HomeView() {

    val topLevelNavigator = ParentNavHostController.current
    val mainViewNavigator = MainViewNavigator.current
    val viewModel = hiltViewModel<HomeRecipeViewModel>()
    val state = viewModel.observeState()

    if (state.list.isNotEmpty()) {
        RefreshView(content = {
            HomeViewContent(list = state.list, viewModel = viewModel)
        }) {
            viewModel.dispatch(RefreshHomeEvent)
        }
    } else {
        LoadingView()
        viewModel.dispatch(LoadRecipeEvent)
    }
    state.viewEffect?.consume()?.let { onViewEffect(it, topLevelNavigator, mainViewNavigator) }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun RefreshView(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
    onRefresh: () -> Unit
) {
    val isRefreshing by remember {
        mutableStateOf(false)
    }
    val pullRefreshState = rememberPullRefreshState(refreshing = isRefreshing, onRefresh = {
        onRefresh()
    })
    Box(
        modifier
            .pullRefresh(pullRefreshState)
    ) {
        content()
        PullRefreshIndicator(
            refreshing = isRefreshing,
            state = pullRefreshState,
            modifier = Modifier.align(Alignment.TopCenter)
        )
    }
}

@ExperimentalMaterialApi
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
                    style = MaterialTheme.typography.h1,
                    modifier = Modifier.padding(8.dp)
                )
                RecipeListWithCuisine(recipe = it, viewModel = viewModel)
            }
        }
    )
}

@ExperimentalMaterialApi
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

@ExperimentalMaterialApi
@Composable
fun RecipeItem(recipe: RecipeModel, onRowClick: OnClick<Int>) {
    Card(
        onClick = { onRowClick(recipe.id) },
        modifier = Modifier
            .height(270.dp)
            .width(140.dp)
            .padding(
                horizontal = 12.dp,
                vertical = 8.dp
            )
    ) {
        Column(verticalArrangement = Arrangement.Top) {
            GlideImage(
                imageModel = recipe.imageUrl,
                modifier = Modifier
                    .height(210.dp)
                    .width(140.dp)
            )
            Text(
                text = recipe.title,
                style = MaterialTheme.typography.body2,
                maxLines = 2,
                modifier = Modifier.padding(4.dp)
            )
        }
    }
}

@ExperimentalMaterialApi
@Composable
fun ViewAll(dispatch: Dispatch<Unit>) {
    Card(
        modifier = Modifier
            .clickable { dispatch(Unit) }
            .height(270.dp)
            .width(140.dp)
            .padding(
                horizontal = 12.dp,
                vertical = 8.dp
            )
    ) {
        Column(verticalArrangement = Arrangement.Center) {
            Text(
                text = stringResource(id = R.string.view_all),
                style = MaterialTheme.typography.h1,
                maxLines = 2,
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}

@Composable
fun onViewEffect(
    viewEffect: ViewEffect,
    parentNavigation: AppMainNavigation,
    appMainNavigation: AppMainNavigation
) {
    when (viewEffect) {
        is ViewAllViewEffect ->
            appMainNavigation.navigateTo(RecipeListIntent(cuisine = viewEffect.cuisine))
        is ViewRecipesDetailViewEffect ->
            parentNavigation.navigateTo(RecipeDetailIntent(detailId = viewEffect.recipeId))
    }
}
