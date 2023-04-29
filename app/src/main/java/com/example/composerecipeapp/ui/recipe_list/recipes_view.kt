package com.example.composerecipeapp.ui.recipe_list

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.composerecipeapp.R
import com.core.platform.functional.ViewEffect
import com.example.composerecipeapp.platform.navigation.navigator.AppMainNavigation
import com.example.composerecipeapp.ui.destinations.RecipeDetailIntent
import com.example.composerecipeapp.ui.util.Dispatch
import com.example.composerecipeapp.ui.util.Navigate
import com.example.composerecipeapp.ui.util.OnClick
import com.domain.common.pojo.RecipeModel
import com.example.composerecipeapp.ui.provider.ParentNavHostController
import com.example.composerecipeapp.ui.theme.ComposeRecipeAppTheme
import com.example.composerecipeapp.ui.views.*
import com.example.composerecipeapp.util.observeState
import com.example.composerecipeapp.viewmodel.recipe_list.*
import com.skydoves.landscapist.glide.GlideImage
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@ExperimentalCoroutinesApi
@Composable
fun RecipeListScreen(
    cuisineKey: String,
    recipesViewModel: RecipeListViewmodel = hiltViewModel()
) {
    val topLevelNavigator = ParentNavHostController.current
    val cuisine = remember { cuisineKey }
    val recipeState = recipesViewModel.observeState()
    val snackBarState = remember{
        SnackbarHostState()
    }

    LaunchedEffect(cuisine) {
        recipesViewModel.dispatch(LoadRecipes(cuisine))
    }
    Scaffold(
        content = {
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
        },
        snackbarHost = { SnackbarHost(snackBarState) }
    )
    recipeState.viewEffect?.consume()
        ?.let {
            RecipeViewEffect(viewEffect = it, snackBarState)
        }
}


@Composable
fun RecipeScreenContent(
    cuisine: String,
    recipeState: RecipeListState,
    recipesViewModel: RecipeListViewmodel,
    navigator: AppMainNavigation
) {
    if (recipeState.isLoading && !recipeState.isPaginate)
        LoadingView()
    RecipeList(
        recipeList = recipeState.recipes.allRecipes,
        dispatch = {
            recipesViewModel.dispatch(it)
        },
        navigate = {
            navigator.navigateTo(it)
        },
        showPaginationLoading = recipeState.isLoading && recipeState.isPaginate,
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
                                dispatch(SaveRecipeEvent(it))
                            },
                            {
                                dispatch(RemoveSavedRecipeEvent(it))
                            }
                        )
                    }
                    val totalItem = scrollState.layoutInfo.totalItemsCount
                    if (index == (totalItem - 1)) {
                        LaunchedEffect(true) {
                            if (!endOfList)
                                dispatch(
                                    LoadRecipes(
                                        isPaginate = true,
                                        query = keyword
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
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeListItem(
    recipe: RecipeModel,
    index: Int,
    onRowClick: OnClick<Int>,
    onSaveClick: OnClick<RecipeModel>,
    onRemoveClick: OnClick<RecipeModel>
) {
    Card(
        onClick = { onRowClick.invoke(recipe.id) },
        modifier = Modifier
            .fillMaxSize()
            .padding(
                horizontal = 12.dp,
                vertical = if (index == 0) 8.dp else 4.dp
            )
    ) {
        Row {
            GlideImage(
                imageModel = recipe.imageUrl,
                modifier = Modifier
                    .height(120.dp)
                    .width(120.dp),
            )
            Column(modifier = Modifier.padding(8.dp)) {
                Text(text = recipe.title, style = MaterialTheme.typography.displayLarge)
                CookingTime(time = recipe.cookingTime.toString())
                Servings(serving = recipe.servings.toString())
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                    SaveIcon(isSaved = recipe.isSaved) {
                        if (it)
                            onSaveClick(recipe)
                        else
                            onRemoveClick(recipe)
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun CookingTimePreview() {
    ComposeRecipeAppTheme {
        Card(
            modifier = Modifier
                .height(100.dp)
                .width(100.dp)
        ) {
            CookingTime(time = "45")
        }
    }
}

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun RecipeViewEffect(viewEffect: ViewEffect, snackBarState: SnackbarHostState) {

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
