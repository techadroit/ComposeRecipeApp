package com.example.composerecipeapp.ui.home_view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.archerviewmodel.ArcherViewModel
import com.core.navigtion.AppNavigator
import com.example.composerecipeapp.R
import com.core.platform.functional.ViewEffect
import com.example.composerecipeapp.ui.util.Dispatch
import com.example.composerecipeapp.ui.util.OnClick
import com.domain.common.pojo.RecipeModel
import com.domain.recipe.cuisines.RecipeWithCuisine
import com.example.composerecipeapp.util.observeState
import com.example.composerecipeapp.viewmodel.home_recipes.*
import com.feature.common.ui.common_views.LoadingView
import com.feature.common.ui.common_views.RefreshView
import com.recipe.app.navigation.intent.RecipeDetailIntent
import com.recipe.app.navigation.intent.RecipeListIntent
import com.recipe.app.navigation.provider.MainViewNavigator
import com.recipe.app.navigation.provider.ParentNavHostController
import com.skydoves.landscapist.glide.GlideImage

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
    }
    state.viewEffect?.consume()?.let { onViewEffect(it, topLevelNavigator, mainViewNavigator) }
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
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 2,
                modifier = Modifier.padding(4.dp)
            )
        }
    }
}

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
    }
}
