package com.example.composerecipeapp.ui.saved_recipe

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.key
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.composerecipeapp.R
import com.example.composerecipeapp.ui.util.Dispatch
import com.example.composerecipeapp.ui.util.Navigate
import com.example.composerecipeapp.ui.destinations.RecipeDetailIntent
import com.example.composerecipeapp.ui.pojo.RecipeModel
import com.example.composerecipeapp.ui.provider.ParentNavHostController
import com.example.composerecipeapp.ui.recipe_list.RecipeListItem
import com.example.composerecipeapp.ui.views.LoadingView
import com.example.composerecipeapp.ui.views.RefreshView
import com.example.composerecipeapp.util.observeState
import com.example.composerecipeapp.viewmodel.save_recipe.*


@Composable
fun FavouriteRecipeScreen(
    viewModel: SaveRecipeViewModel = hiltViewModel(),
) {
    val topLevelNavigator = ParentNavHostController.current
    LaunchedEffect(true) {
        viewModel.dispatch(LoadRecipe())
    }
    val state = viewModel.observeState()
    if (state.isLoading) {
        LoadingView()
    } else {
        val list = state.recipeData.allRecipes
        if (list.isEmpty()) {
            EmptyView()
        } else {
            RefreshView(content = {
                RecipeList(
                    recipeList = state.recipeData.allRecipes,
                    dispatch = {
                        viewModel.dispatch(it)
                    },
                    navigate = {
                        topLevelNavigator.navigateTo(it)
                    }
                )
            }) {
                viewModel.dispatch(RefreshViewEvent)
            }
        }
    }
}


@Composable
fun EmptyView() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(stringResource(id = R.string.no_saved_recipe), style = MaterialTheme.typography.displayLarge)
    }
}


@Composable
fun RecipeList(
    recipeList: List<RecipeModel>,
    dispatch: Dispatch<SaveRecipeEvent>,
    navigate: Navigate
) {
    val scrollState = rememberLazyListState()
    Column(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            state = scrollState,
            contentPadding = PaddingValues(bottom = 80.dp),
            content = {
                itemsIndexed(recipeList) { index, recipe ->
                    key(index) {
                        RecipeListItem(
                            recipe = recipe,
                            index = index,
                            {
                                navigate(RecipeDetailIntent(detailId = it.toString()))
                            },
                            {
                            },
                            {
                                dispatch(RemoveRecipe(recipe))
                            }
                        )
                    }
                }
            }
        )
    }
}
