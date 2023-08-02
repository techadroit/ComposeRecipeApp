package com.feature.recipe.video.state

import com.core.platform.exception.Failure
import com.domain.common.pojo.VideoRecipeModel
import com.state_manager.state.AppState

data class RecipeVideoState(
    val data: List<VideoRecipeModel> = emptyList(),
    val query: String? = null,
    val isLoading: Boolean = true,
    val isPaginate: Boolean = false,
    val failure: Failure? = null
) : AppState

fun RecipeVideoState.onFailure(failure: Failure?) = copy(failure = failure)

fun RecipeVideoState.showLoading(isLoading: Boolean = true) =
    copy(isLoading = isLoading).onFailure(null)

fun RecipeVideoState.onSuccess(recipeModel: List<VideoRecipeModel>, isPaginate: Boolean = false) =
    this.copy(data = this.data + recipeModel, isPaginate = isPaginate, isLoading = false)
        .onFailure(null)

fun RecipeVideoState.onLoading(isPaginate: Boolean = false, isLoading: Boolean = true) =
    this.copy(isPaginate = isPaginate, isLoading = isLoading).onFailure(null)

fun RecipeVideoState.setQuery(query: String) =
    this.copy(query = query)
