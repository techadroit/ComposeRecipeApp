package com.feature.recipe.detail.state

import com.state_manager.state.ArcherState
import com.core.platform.exception.Failure
import com.domain.common.pojo.RecipeDetailModel
import com.domain.common.pojo.RecipeModel

data class RecipeDetailState(
    val isLoading: Boolean = false,
    val recipeDetail: RecipeDetailModel? = null,
    val similarRecipe: List<RecipeModel>? = null,
    val failure: Failure? = null
) : ArcherState

fun RecipeDetailState.onSuccessResponse(
    recipeDetail: RecipeDetailModel,
    recipeList: List<RecipeModel>
) =
    this.copy(isLoading = false, recipeDetail = recipeDetail, similarRecipe = recipeList)

fun RecipeDetailState.onError(failure: Failure) = this.copy(isLoading = false, failure = failure)

fun RecipeDetailState.onLoading() = this.copy(isLoading = true)

fun RecipeDetailState.onRemoveFromSavedList() =
    this.copy(recipeDetail = this.recipeDetail?.copy(isSaved = false))

fun RecipeDetailState.onRecipeSaved() =
    this.copy(recipeDetail = this.recipeDetail?.copy(isSaved = true))
