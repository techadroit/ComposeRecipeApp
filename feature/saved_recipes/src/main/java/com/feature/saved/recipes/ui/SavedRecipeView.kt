package com.feature.saved.recipes.ui

import RecipeListItem
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
import androidx.hilt.navigation.compose.hiltViewModel
import com.core.themes.dimension
import com.domain.common.pojo.RecipeModel
import com.feature.common.Dispatch
import com.feature.common.Navigate
import com.feature.common.observeState
import com.feature.common.ui.common_views.LoadingView
import com.feature.common.ui.common_views.RefreshView
import com.feature.saved.recipes.R
import com.feature.saved.recipes.state.LoadRecipe
import com.feature.saved.recipes.state.RefreshViewEvent
import com.feature.saved.recipes.state.RemoveRecipe
import com.feature.saved.recipes.state.SaveRecipeEvent
import com.recipe.app.navigation.intent.RecipeDetailIntent
import com.recipe.app.navigation.provider.ParentNavHostController

@Composable
fun FavouriteRecipeScreen(
    viewModel: com.feature.saved.recipes.viewmodel.SaveRecipeViewModel = hiltViewModel(),
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
        Text(
            stringResource(id = R.string.no_saved_recipe),
            style = MaterialTheme.typography.displayLarge
        )
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
            contentPadding = PaddingValues(bottom = MaterialTheme.dimension().contentPadding),
            content = {
                itemsIndexed(recipeList) { index, recipe ->
                    key(index) {
                        RecipeListItem(
                            title = recipe.title,
                            imageUrl = recipe.imageUrl,
                            cookingTime = recipe.cookingTime,
                            servings = recipe.servings,
                            isSaved = recipe.isSaved,
                            index = index,
                            onRowClick = {
                                navigate(RecipeDetailIntent(detailId = recipe.id.toString()))
                            },
                            onSaveClick = {

                            },
                            onRemoveClick = {
                                dispatch(RemoveRecipe(recipe))
                            }
                        )
                    }
                }
            }
        )
    }
}
