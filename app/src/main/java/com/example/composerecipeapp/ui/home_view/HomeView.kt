package com.example.composerecipeapp.ui.home_view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.key
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.composerecipeapp.R
import com.example.composerecipeapp.domain.usecases.RecipeWithCuisine
import com.example.composerecipeapp.ui.Dispatch
import com.example.composerecipeapp.ui.pojo.RecipeModel
import com.example.composerecipeapp.ui.provider.ParentNavHostController
import com.example.composerecipeapp.ui.views.LoadingView
import com.example.composerecipeapp.util.observeState
import com.example.composerecipeapp.viewmodel.home_recipes.*
import com.skydoves.landscapist.glide.GlideImage

@ExperimentalMaterialApi
@Composable
fun HomeView(navController: NavHostController) {
    val viewModel = hiltViewModel<HomeRecipeViewModel>()
    val state = viewModel.observeState()
    if (state.list.isNotEmpty()) {
        LazyColumn(
            content = {
                items(state.list) {
                    Text(
                        text = it.cuisine,
                        style = MaterialTheme.typography.h1,
                        modifier = Modifier.padding(8.dp)
                    )
                    RecipeListWithCuisine(recipe = it, viewModel = viewModel)
                }
            }
        )
    } else {
        LoadingView()
    }
    LaunchedEffect(true) {
        viewModel.dispatch(LoadRecipeEvent)
    }
    state.sideEffect?.consume()?.let { onSideEffect(it, navController = navController) }
}

@ExperimentalMaterialApi
@Composable
fun RecipeListWithCuisine(
    recipe: RecipeWithCuisine,
    viewModel: HomeRecipeViewModel
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
fun RecipeItem(recipe: RecipeModel, onRowClick: (Int) -> Unit) {
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
fun onSideEffect(sideEffect: SideEffect, navController: NavHostController) {
    when (sideEffect) {
        is SideEffect.ViewAllSideEffect ->
            navController.navigate("recipes/${sideEffect.cuisine}")
        is SideEffect.ViewRecipesDetailSideEffect -> {
            val navHostController = ParentNavHostController.current
            navHostController.navigate("recipe_details/${sideEffect.recipeId}")
        }
    }
}
