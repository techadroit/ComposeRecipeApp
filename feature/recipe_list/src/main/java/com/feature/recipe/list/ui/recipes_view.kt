package com.feature.recipe.list.ui

import RecipeListItem
import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.core.navigtion.AppNavigator
import com.core.themes.dimension
import com.domain.common.pojo.RecipeModel
import com.feature.common.Dispatch
import com.feature.common.Navigate
import com.state_manager.ui.observeSideEffects
import com.state_manager.ui.getState
import com.feature.common.ui.common_views.*
import com.feature.recipe.list.R
import com.feature.recipe.list.state.LoadRecipes
import com.feature.recipe.list.state.OnSavedRecipe
import com.feature.recipe.list.state.RecipeEvent
import com.feature.recipe.list.state.RecipeListState
import com.feature.recipe.list.state.RefreshRecipeList
import com.feature.recipe.list.state.RemoveSavedRecipeEvent
import com.feature.recipe.list.state.SaveRecipeEvent
import com.feature.recipe.list.state.showLoading
import com.feature.recipe.list.state.showPagination
import com.feature.recipe.list.viewmodel.RecipeListViewmodel
import com.recipe.app.navigation.intent.RecipeDetailIntent
import com.recipe.app.navigation.provider.ParentNavHostController
import com.state_manager.side_effects.SideEffect
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@ExperimentalCoroutinesApi
@Composable
fun RecipeListScreen(
    cuisineKey: String, recipesViewModel: RecipeListViewmodel = hiltViewModel()
) {
    val topLevelNavigator = ParentNavHostController.current
    val cuisine = remember { cuisineKey }
    val recipeState = recipesViewModel.getState()
    val snackBarState = remember {
        SnackbarHostState()
    }

    LaunchedEffect(cuisine) {
        recipesViewModel.dispatch(LoadRecipes(cuisine))
    }
    Scaffold(content = {
        RefreshView(content = {
            RecipeScreenContent(
                cuisine = cuisine,
                recipeState = recipeState,
                recipesViewModel = recipesViewModel,
                navigator = topLevelNavigator
            )
        }) {
            recipesViewModel.dispatch(RefreshRecipeList)
        }
    }, snackbarHost = { SnackbarHost(snackBarState) })

    recipesViewModel.observeSideEffects {
        RecipeViewEffect(viewEffect = it, snackBarState = snackBarState)
    }
}


@Composable
fun RecipeScreenContent(
    cuisine: String,
    recipeState: RecipeListState,
    recipesViewModel: RecipeListViewmodel,
    navigator: AppNavigator
) {
    if (recipeState.showLoading()) LoadingView()
    RecipeList(recipeList = recipeState.recipes.allRecipes,
        dispatch = {
            recipesViewModel.dispatch(it)
        },
        navigate = {
            navigator.navigateTo(it)
        },
        showPaginationLoading = recipeState.showPagination(),
        keyword = cuisine,
        endOfList = recipeState.endOfItems
    )
}

@Composable
fun RecipeList(
    recipeList: List<RecipeModel>,
    dispatch: Dispatch<RecipeEvent>,
    navigate: Navigate,
    showPaginationLoading: Boolean,
    keyword: String,
    endOfList: Boolean
) {
    val scrollState = rememberLazyListState()
    Column(modifier = Modifier.fillMaxSize()) {
        LazyColumn(state = scrollState,
            contentPadding = PaddingValues(bottom = MaterialTheme.dimension().contentPadding),
            content = {
                itemsIndexed(recipeList) { index, recipe ->
                    key(index) {
                        RecipeListItem(title = recipe.title,
                            imageUrl = recipe.imageUrl,
                            cookingTime = recipe.cookingTime,
                            servings = recipe.servings,
                            isSaved = recipe.isSaved,
                            index = index,
                            onRowClick = {
                                navigate(RecipeDetailIntent(detailId = recipe.id.toString()))
                            },
                            onSaveClick = {
                                dispatch(SaveRecipeEvent(recipe))
                            },
                            onRemoveClick = {
                                dispatch(RemoveSavedRecipeEvent(recipe))
                            })
                    }
                    val totalItem = scrollState.layoutInfo.totalItemsCount
                    if (index == (totalItem - 1)) {
                        LaunchedEffect(true) {
                            if (!endOfList) dispatch(
                                LoadRecipes(
                                    isPaginate = true, query = keyword
                                )
                            )
                        }
                    }
                }
                if (showPaginationLoading) {
                    item {
                        PaginationLoading()
                    }
                }
                if (endOfList && recipeList.isEmpty()) {
                    item {
                        Text(stringResource(id = R.string.no_result))
                    }
                }
            })
    }
}

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun RecipeViewEffect(viewEffect: SideEffect, snackBarState: SnackbarHostState) {

    val scope = rememberCoroutineScope()
    when (viewEffect) {
        is OnSavedRecipe -> {
            val message = stringResource(id = R.string.recipe_saved_text)
            scope.launch {
                snackBarState.showSnackbar(message = message)
            }
        }
    }
}
