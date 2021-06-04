package com.example.composerecipeapp.viewmodel.recipe_detail

import com.example.composerecipeapp.core.exception.Failure
import com.example.composerecipeapp.core.viewmodel.AppState
import com.example.composerecipeapp.ui.pojo.RecipeDetailModel
import com.example.composerecipeapp.ui.pojo.RecipeModel


data class RecipeDetailState(
    val isLoading: Boolean = false,
    val recipeDetail: RecipeDetailModel? = null,
    val similarRecipe: List<RecipeModel>? = null,
    val failure: Failure? = null
) : AppState

fun RecipeDetailState.onSuccessResponse(
    recipeDetail: RecipeDetailModel,
    recipeList: List<RecipeModel>
) =
    this.copy(isLoading = false, recipeDetail = recipeDetail, similarRecipe = recipeList)

fun RecipeDetailState.onError(failure: Failure) = this.copy(isLoading = false, failure = failure)

fun RecipeDetailState.onLoading() = this.copy(isLoading = true)
