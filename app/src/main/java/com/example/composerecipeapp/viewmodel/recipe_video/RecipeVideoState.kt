package com.example.composerecipeapp.viewmodel.recipe_video

import com.example.composerecipeapp.core.viewmodel.AppState
import com.example.composerecipeapp.ui.pojo.VideoRecipeModel

data class RecipeVideoState(
    val data: List<VideoRecipeModel> = emptyList(),
    val isLoading: Boolean = false,
    val isPaginate: Boolean = false
) : AppState

fun RecipeVideoState.onSuccess(recipeModel: List<VideoRecipeModel>, isPaginate: Boolean = false) =
    this.copy(data = this.data + recipeModel, isPaginate = isPaginate, isLoading = false)

fun RecipeVideoState.onLoading(isPaginate: Boolean = false) =
    this.copy(isPaginate = isPaginate, isLoading = true)
