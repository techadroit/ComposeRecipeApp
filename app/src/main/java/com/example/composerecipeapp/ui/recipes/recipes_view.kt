package com.example.composerecipeapp.ui.recipes

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.key
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.navigate
import com.example.composerecipeapp.ui.pojo.RecipeModel
import com.recipeapp.view.viewmodel.LoadRecipes
import com.recipeapp.view.viewmodel.RecipeListViewmodel
import com.skydoves.landscapist.glide.GlideImage
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@Composable
fun RecipeView(navHostController: NavHostController) {

    val recipesViewmodel: RecipeListViewmodel =
        viewModel(modelClass = RecipeListViewmodel::class.java)
    LaunchedEffect(true) {
        recipesViewmodel.add(LoadRecipes())
    }

    val recipeState = recipesViewmodel.stateEmitter.collectAsState().value

    if (recipeState.isLoading && !recipeState.isPaginate)
        LoadingView()
    RecipeList(
        recipeList = recipeState.recipes.allRecipes,
        recipesViewmodel = recipesViewmodel,
        navHostController = navHostController,
        showPaginationLoading = recipeState.isLoading && recipeState.isPaginate
    )
}

@Composable
fun RecipeList(
    recipeList: List<RecipeModel>,
    recipesViewmodel: RecipeListViewmodel,
    navHostController: NavHostController,
    showPaginationLoading: Boolean
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
                            navHostController = navHostController
                        )
                    }
                    val totalItem = scrollState.layoutInfo.totalItemsCount
                    if (index == (totalItem - 1)) {
                        LaunchedEffect(true) {
                            recipesViewmodel.add(LoadRecipes(isPaginate = true))
                        }
                    }
                }
                if (showPaginationLoading) {
                    item {
                        PaginationLoading()
                    }
                }
            })
    }
}

@Composable
fun RecipeListItem(recipe: RecipeModel, index: Int, navHostController: NavHostController) {
    println(" i was composed $index")
    Card(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                horizontal = 12.dp,
                vertical = if (index == 0) 8.dp else 4.dp
            )
            .clickable(onClick = {
                navHostController.navigate("recipe_details")
            })
    ) {
        Row {
            GlideImage(
                imageModel = recipe.imageUrl,
                modifier = Modifier
                    .height(120.dp)
                    .width(120.dp),
            )
            Column(modifier = Modifier.padding(8.dp)) {
                Text(text = recipe.title)
                Text(text = recipe.cookingTime.toString() + " mint")
            }
        }
    }
}

@Composable
fun LoadingView() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator(
            modifier = Modifier
                .height(40.dp)
                .width(40.dp),
            color = Color.Blue
        )
    }
}

@Composable
fun PaginationLoading() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator(
            modifier = Modifier
                .height(40.dp)
                .width(40.dp),
            color = Color.Blue
        )
    }
}

