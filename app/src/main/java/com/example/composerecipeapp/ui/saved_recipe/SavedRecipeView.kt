package com.example.composerecipeapp.ui.saved_recipe

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.key
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.composerecipeapp.ui.Dispatch
import com.example.composerecipeapp.ui.Navigate
import com.example.composerecipeapp.ui.pojo.RecipeModel
import com.example.composerecipeapp.ui.provider.ParentNavHostController
import com.example.composerecipeapp.ui.recipe_list.RecipeListItem
import com.example.composerecipeapp.ui.views.LoadingView
import com.example.composerecipeapp.viewmodel.save_recipe.LoadRecipe
import com.example.composerecipeapp.viewmodel.save_recipe.RemoveRecipe
import com.example.composerecipeapp.viewmodel.save_recipe.SaveRecipeEvent
import com.example.composerecipeapp.viewmodel.save_recipe.SaveRecipeViewModel

@Composable
fun SaveRecipeView(viewModel: SaveRecipeViewModel = hiltViewModel()) {

    LaunchedEffect(true) {
        viewModel.dispatch(LoadRecipe())
    }
    val state = viewModel.stateEmitter.collectAsState().value
    val navHostController = ParentNavHostController.current
    if (state.isLoading) {
        LoadingView()
    } else {
        val list = state.recipeData.allRecipes
        if (list.isEmpty()) {
            EmptyView()
        } else
            RecipeList(
                recipeList = state.recipeData.allRecipes,
                dispatch = {
                    viewModel.dispatch(it)
                },
                navigate = {
                    navHostController.navigate(it)
                }
            )
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
        Text(text = "No Saved Recipes", style = MaterialTheme.typography.h1)
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
                                navigate("recipe_details/${it}")
                            }, {
                            },{
                                dispatch(RemoveRecipe(recipe))
                            }
                        )
                    }
                }
            })
    }
}

