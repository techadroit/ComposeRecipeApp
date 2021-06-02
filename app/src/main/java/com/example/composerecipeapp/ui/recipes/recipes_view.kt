package com.example.composerecipeapp.ui.recipes

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.VerticalAlignmentLine
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.navigate
import com.example.composerecipeapp.ParentNavHostController
import com.example.composerecipeapp.ui.ComposeRecipeAppTheme
import com.example.composerecipeapp.ui.pojo.RecipeModel
import com.recipeapp.view.viewmodel.LoadRecipes
import com.recipeapp.view.viewmodel.RecipeListViewmodel
import com.skydoves.landscapist.glide.GlideImage
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@Composable
fun RecipeView(navHostController: NavHostController = ParentNavHostController.current,key: String?) {

    val recipesViewmodel: RecipeListViewmodel =
        viewModel(modelClass = RecipeListViewmodel::class.java)
    val keyword = remember{key ?: "chicken"}
    LaunchedEffect(true) {
        recipesViewmodel.dispatch(LoadRecipes(keyword))
    }

    val recipeState = recipesViewmodel.stateEmitter.collectAsState().value

    if (recipeState.isLoading && !recipeState.isPaginate)
        LoadingView()
    RecipeList(
        recipeList = recipeState.recipes.allRecipes,
        recipesViewmodel = recipesViewmodel,
        navHostController = navHostController,
        showPaginationLoading = recipeState.isLoading && recipeState.isPaginate,
        keyword = keyword,
        endOfList = recipeState.endOfItems
    )
}

@Composable
fun RecipeList(
    recipeList: List<RecipeModel>,
    recipesViewmodel: RecipeListViewmodel,
    navHostController: NavHostController,
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
                            navHostController = navHostController
                        )
                    }
                    val totalItem = scrollState.layoutInfo.totalItemsCount
                    if (index == (totalItem - 1)) {
                        LaunchedEffect(true) {
                            if(!endOfList)
                            recipesViewmodel.dispatch(LoadRecipes(isPaginate = true,query = keyword))
                        }
                    }
                }
                if (showPaginationLoading) {
                    item {
                        PaginationLoading()
                    }
                }
                if(endOfList && recipeList.isEmpty()){
                    item {
                        Text("Sorry, No result found")
                    }
                }
            })
    }
}

@Composable
fun RecipeListItem(recipe: RecipeModel, index: Int, navHostController: NavHostController) {
    Card(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                horizontal = 12.dp,
                vertical = if (index == 0) 8.dp else 4.dp
            )
            .clickable(onClick = {
                navHostController.navigate("recipe_details/${recipe.id}")
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
                Text(text = recipe.title,style = MaterialTheme.typography.h1)
                CookingTime(time = recipe.cookingTime.toString())
                Servings(serving = recipe.servings.toString())
            }
        }
    }
}

@Composable
fun CookingTime(time:String){
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(imageVector = Icons.Default.DateRange, contentDescription = "Cooking Time",
            tint = Color.LightGray,modifier = Modifier
                .width(14.dp)
                .height(14.dp))
        Spacer(modifier = Modifier.width(2.dp))
        Text(text = "$time mint",style = MaterialTheme.typography.subtitle1)
    }
}

@Composable
fun Servings(serving:String){
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(imageVector = Icons.Default.Person, contentDescription = "Servings",
            tint = Color.LightGray,modifier = Modifier
                .width(14.dp)
                .height(14.dp))
        Spacer(modifier = Modifier.width(2.dp))
        Text(text = "$serving servings",style = MaterialTheme.typography.subtitle1)
    }
}

@Preview
@Composable
fun CookingTimePreview(){
    ComposeRecipeAppTheme{
       Card(modifier = Modifier
           .height(100.dp)
           .width(100.dp)) {
           CookingTime(time = "45")
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
            color = MaterialTheme.colors.onBackground
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
            color = MaterialTheme.colors.onBackground
        )
    }
}

