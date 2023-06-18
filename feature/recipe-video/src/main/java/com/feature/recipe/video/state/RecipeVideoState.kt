package com.feature.recipe.video.state

import com.state_manager.state.AppState
import com.domain.common.pojo.VideoRecipeModel

data class RecipeVideoState(
    val data: List<VideoRecipeModel> = emptyList(),
    val query:String? = null,
    val isLoading: Boolean = true,
    val isPaginate: Boolean = false
) : AppState

fun RecipeVideoState.onSuccess(recipeModel: List<VideoRecipeModel>, isPaginate: Boolean = false) =
    this.copy(data = this.data + recipeModel, isPaginate = isPaginate, isLoading = false)

fun RecipeVideoState.onLoading(isPaginate: Boolean = false) =
    this.copy(isPaginate = isPaginate, isLoading = true)

fun RecipeVideoState.setQuery(query: String) =
    this.copy(query = query)
