package com.example.composerecipeapp.ui.home_view

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.composerecipeapp.util.PreviewHelper
import com.example.composerecipeapp.util.PreviewViewModel
import com.example.composerecipeapp.viewmodel.home_recipes.HomeRecipeState

@ExperimentalMaterialApi
@Preview("HomeViewContent Preview")
@Composable
fun HomeViewContentPreview() {
    HomeViewContent(
        list = listOf(PreviewHelper.recipeWithCuisine, PreviewHelper.recipeWithCuisine),
        viewModel = PreviewViewModel(HomeRecipeState())
    )
}

@ExperimentalMaterialApi
@Preview("RecipeListWithCuisine View")
@Composable
fun RecipeListWithCuisinePreview() {
    val recipe = PreviewHelper.recipeWithCuisine
    RecipeListWithCuisine(recipe = recipe, PreviewViewModel(HomeRecipeState()))
}

@ExperimentalMaterialApi
@Preview("RecipeItem View", widthDp = 300, heightDp = 350)
@Composable
fun RecipeItemPreview() {
    val recipe = PreviewHelper.recipe
    RecipeItem(recipe = recipe) {

    }
}

@ExperimentalMaterialApi
@Preview("View All Button", widthDp = 300, heightDp = 350)
@Composable
fun ViewAllPreview() {
    ViewAll {

    }
}
