package com.example.composerecipeapp.viewmodel.save_recipe

import com.archerviewmodel.state.ArcherState
import com.example.composerecipeapp.viewmodel.recipe_list.RecipeData

data class SaveRecipeState(
    val isLoading: Boolean = false,
    val recipeData: RecipeData = RecipeData(emptyList())
) : ArcherState

fun SaveRecipeState.onLoading() = this.copy(isLoading = true)

fun SaveRecipeState.onSuccess(recipeData: RecipeData) =
    this.copy(isLoading = false, recipeData = recipeData)
