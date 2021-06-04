package com.example.composerecipeapp.ui.saved_recipe

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.key
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.composerecipeapp.ui.pojo.RecipeModel
import com.example.composerecipeapp.ui.provider.ParentNavHostController
import com.example.composerecipeapp.ui.recipe_list.RecipeListItem
import com.example.composerecipeapp.ui.views.LoadingView

@Composable
fun SaveRecipeView(viewModel : SaveRecipeViewModel = hiltViewModel()) {

    LaunchedEffect(true){
        viewModel.dispatch(LoadRecipe())
    }
    val state = viewModel.stateEmitter.collectAsState().value

    if(state.isLoading){
        LoadingView()
    }else{
        RecipeList(
            recipeList = state.recipeData.allRecipes,
            saveRecipeViewModel = viewModel
        )
    }

}


@Composable
fun RecipeList(
    recipeList: List<RecipeModel>,
    saveRecipeViewModel: SaveRecipeViewModel
) {
    val scrollState = rememberLazyListState()
    val navHostController = ParentNavHostController.current
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
                                navHostController.navigate("recipe_details/${it}")
                            },{
//                                recipesViewmodel.saveRecipe(recipeModel = it)
                            }
                        )
                    }
                }
            })
    }
}

