package com.feature.home.state

import com.core.platform.exception.Failure
import com.domain.recipe.cuisines.RecipeWithCuisine
import com.state_manager.state.AppState

data class HomeRecipeState(
    val list: List<RecipeWithCuisine> = emptyList(),
    val isLoadingPage: Boolean = true,
    val failure: Failure? = null
) : AppState

fun HomeRecipeState.onFailure(failure: Failure?) = copy(failure = failure)
fun HomeRecipeState.initialState() = HomeRecipeState(isLoadingPage = false)
fun HomeRecipeState.onLoad(list: List<RecipeWithCuisine>) = copy(list = list)

fun HomeRecipeState.add(items: RecipeWithCuisine) =
    copy(list = list.toMutableList().apply { add(items) })

fun HomeRecipeState.showLoading(isLoading: Boolean) = copy(isLoadingPage = isLoading)




