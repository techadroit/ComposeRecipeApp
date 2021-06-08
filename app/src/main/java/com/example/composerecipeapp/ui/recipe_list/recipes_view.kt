package com.example.composerecipeapp.ui.recipe_list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.composerecipeapp.ui.ComposeRecipeAppTheme
import com.example.composerecipeapp.ui.pojo.RecipeModel
import com.example.composerecipeapp.ui.provider.ParentNavHostController
import com.example.composerecipeapp.ui.views.*
import com.example.composerecipeapp.viewmodel.recipe_list.LoadRecipes
import com.example.composerecipeapp.viewmodel.recipe_list.RecipeListViewmodel
import com.skydoves.landscapist.glide.GlideImage
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@Composable
fun RecipeView(
    key: String?
) {

    val recipesViewmodel: RecipeListViewmodel = hiltViewModel()
    val keyword = remember { key ?: "chicken" }
    LaunchedEffect(true) {
        recipesViewmodel.dispatch(LoadRecipes(keyword))
    }

    val recipeState = recipesViewmodel.stateEmitter.collectAsState().value

    if (recipeState.isLoading && !recipeState.isPaginate)
        LoadingView()
    RecipeList(
        recipeList = recipeState.recipes.allRecipes,
        recipesViewmodel = recipesViewmodel,
        showPaginationLoading = recipeState.isLoading && recipeState.isPaginate,
        keyword = keyword,
        endOfList = recipeState.endOfItems
    )
}

@Composable
fun RecipeList(
    recipeList: List<RecipeModel>,
    recipesViewmodel: RecipeListViewmodel,
    showPaginationLoading: Boolean,
    keyword: String,
    endOfList: Boolean
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
                                recipesViewmodel.saveRecipe(recipeModel = it)
                            }
                        )
                    }
                    val totalItem = scrollState.layoutInfo.totalItemsCount
                    if (index == (totalItem - 1)) {
                        LaunchedEffect(true) {
                            if (!endOfList)
                                recipesViewmodel.dispatch(
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
                        Text("Sorry, No result found")
                    }
                }
            })
    }
}


@Composable
fun RecipeListItem(recipe: RecipeModel, index: Int, onRowClick: (Int) -> Unit, onSaveClick : (RecipeModel) ->Unit) {
    Card(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                horizontal = 12.dp,
                vertical = if (index == 0) 8.dp else 4.dp
            )
            .clickable(onClick = {
                onRowClick.invoke(recipe.id)
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
                Text(text = recipe.title, style = MaterialTheme.typography.h1)
                CookingTime(time = recipe.cookingTime.toString())
                Servings(serving = recipe.servings.toString())
                Row(modifier = Modifier.fillMaxWidth(),horizontalArrangement = Arrangement.End){
                    SaveIcon(isSaved = recipe.isSaved){
                        onSaveClick(recipe)
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
