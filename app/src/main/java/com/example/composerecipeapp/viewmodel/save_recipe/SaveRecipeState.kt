package com.example.composerecipeapp.viewmodel.save_recipe

import com.example.composerecipeapp.core.viewmodel.AppState
import com.example.composerecipeapp.viewmodel.recipe_list.RecipeData

data class SaveRecipeState(
    val isLoading: Boolean = false,
    val recipeData: RecipeData = RecipeData(emptyList())
) : AppState

fun SaveRecipeState.onLoading() = this.copy(isLoading = true)

fun SaveRecipeState.onSuccess(recipeData: RecipeData) =
    this.copy(isLoading = false,recipeData = recipeData)
