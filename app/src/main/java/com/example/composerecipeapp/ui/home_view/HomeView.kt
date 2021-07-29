package com.example.composerecipeapp.ui.home_view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.composerecipeapp.domain.usecases.RecipeWithCuisine
import com.example.composerecipeapp.ui.Dispatch
import com.example.composerecipeapp.ui.Navigate
import com.example.composerecipeapp.ui.pojo.RecipeModel
import com.example.composerecipeapp.ui.views.LoadingView
import com.example.composerecipeapp.util.observeState
import com.example.composerecipeapp.viewmodel.home_recipes.HomeRecipeViewModel
import com.example.composerecipeapp.viewmodel.home_recipes.LoadRecipeEvent
import com.example.composerecipeapp.viewmodel.save_recipe.SaveRecipeEvent
import com.skydoves.landscapist.glide.GlideImage

@ExperimentalMaterialApi
@Composable
fun HomeView() {
    val viewModel = hiltViewModel<HomeRecipeViewModel>()
    val state = viewModel.observeState()
    if (state.list.isNotEmpty()) {
        Surface {
            Column(modifier = Modifier.wrapContentSize()) {
                LazyColumn(content = {
                    items(state.list){
                        Text(
                            text = it.cuisine,
                            style = MaterialTheme.typography.h1,
                            modifier = Modifier.padding(8.dp)
                        )
                        RecipeListWithCuisine(recipe = it, dispatch = {}, navigate = {})
                    }
                })
            }
        }
    } else {
        LoadingView()
    }
    viewModel.dispatch(LoadRecipeEvent)
}

@ExperimentalMaterialApi
@Composable
fun RecipeListWithCuisine(
    recipe: RecipeWithCuisine,
    dispatch: Dispatch<SaveRecipeEvent>,
    navigate: Navigate
) {
    val scrollState = rememberLazyListState()
    Column(modifier = Modifier.wrapContentSize()) {
        LazyRow(
            state = scrollState,
            contentPadding = PaddingValues(bottom = 80.dp),
            content = {
                itemsIndexed(recipe.recipeList) { index, recipe ->
                    key(index) {
                        RecipeItem(
                            recipe = recipe,
                            index = index
                        ) {
                        }
                    }
                }
            })
    }
}

@ExperimentalMaterialApi
@Composable
fun RecipeItem(recipe: RecipeModel, index: Int, onRowClick: (Int) -> Unit) {
    Card(
        onClick = { onRowClick.invoke(recipe.id) },
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
