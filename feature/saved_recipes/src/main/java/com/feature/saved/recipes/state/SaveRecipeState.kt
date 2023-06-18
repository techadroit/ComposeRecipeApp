package com.feature.saved.recipes.state

import com.state_manager.state.AppState
import com.feature.saved.recipes.model.SavedData

data class SaveRecipeState(
    val isLoading: Boolean = false,
    val recipeData: SavedData = SavedData(emptyList())
) : AppState

fun SaveRecipeState.onLoading() = this.copy(isLoading = true)

fun SaveRecipeState.onSuccess(recipeData: SavedData) =
    this.copy(isLoading = false, recipeData = recipeData)
