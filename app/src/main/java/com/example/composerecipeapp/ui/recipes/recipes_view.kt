package com.example.composerecipeapp.ui.recipes

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Card
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.key
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.navigate
import com.example.composerecipeapp.core.Resource
import com.example.composerecipeapp.ui.pojo.RecipeModel
import com.skydoves.landscapist.glide.GlideImage
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@Composable
fun recipeView(navHostController: NavHostController) {

    val recipesViewmodel: RecipesViewModel = viewModel(modelClass = RecipesViewModel::class.java)
    LaunchedEffect(true) {
        recipesViewmodel.add(LoadRecipes())
    }

    val recipeState = recipesViewmodel.stateEmitter.collectAsState().value
    when (recipeState.data) {
        is Resource.Success -> {
            val recipeList = recipeState.recipeList
            recipeList(
                recipeList = recipeList,
                recipesViewmodel = recipesViewmodel,
                navHostController = navHostController
            )
        }
        else -> {
            renderText(message = "loading")
        }
    }
}

@Composable
fun recipeList(
    recipeList: List<RecipeModel>,
    recipesViewmodel: RecipesViewModel,
    navHostController: NavHostController
) {
    val scrollState = rememberLazyListState()
    LazyColumn(
        state = scrollState,
        contentPadding = PaddingValues(bottom = 80.dp),
        content = {
            itemsIndexed(recipeList) { index, recipe ->
                key(index) {
                    recipeListItem(
                        recipe = recipe,
                        index = index,
                        navHostController = navHostController
                    )
                }
                val totalItem = scrollState.layoutInfo.totalItemsCount
                if (index == (totalItem - 1)) {
                    LaunchedEffect(true) {
                        recipesViewmodel.add(LoadRecipes())
                    }
                }
            }
        })
}

@Composable
fun recipeListItem(recipe: RecipeModel, index: Int, navHostController: NavHostController) {
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
fun renderText(message: String) {
    Surface(modifier = Modifier.fillMaxSize()) {
        Text(text = message)
    }
}
