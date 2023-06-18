package com.feature.saved.recipes.state

import com.state_manager.state.ArcherState
import com.feature.saved.recipes.model.SavedData

data class SaveRecipeState(
    val isLoading: Boolean = false,
    val recipeData: SavedData = SavedData(emptyList())
) : ArcherState

fun SaveRecipeState.onLoading() = this.copy(isLoading = true)

fun SaveRecipeState.onSuccess(recipeData: SavedData) =
    this.copy(isLoading = false, recipeData = recipeData)
