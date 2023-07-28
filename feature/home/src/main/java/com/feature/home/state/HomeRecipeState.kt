package com.feature.home.state

import com.core.platform.exception.Failure
import com.core.platform.functional.ViewEffect
import com.domain.recipe.cuisines.RecipeWithCuisine
import com.state_manager.extensions.Consumable
import com.state_manager.extensions.asConsumable
import com.state_manager.side_effects.SideEffect
import com.state_manager.state.AppState

data class HomeRecipeState(
    val list: List<RecipeWithCuisine> = emptyList(),
    val viewEffect: Consumable<ViewEffect>? = null,
    val isLoadingPage: Boolean = true
) : AppState

fun HomeRecipeState.initialState() = HomeRecipeState(isLoadingPage = false)
fun HomeRecipeState.onLoad(list: List<RecipeWithCuisine>) = copy(list = list)

fun HomeRecipeState.add(items: RecipeWithCuisine) =
    copy(list = list.toMutableList().apply { add(items) })

fun HomeRecipeState.onViewEffect(viewEffect: ViewEffect) =
    copy(viewEffect = viewEffect.asConsumable())

fun HomeRecipeState.showLoading(isLoading:Boolean) = copy(isLoadingPage = isLoading)

fun HomeRecipeState.viewAll(cuisine: String) = onViewEffect(ViewAllViewEffect(cuisine))

fun HomeRecipeState.viewDetail(recipeId: String) = onViewEffect(ViewRecipesDetailViewEffect(recipeId))

fun HomeRecipeState.onLoadingError() = onViewEffect(LoadingError("Unable to Load"))
    .showLoading(false)

data class OnFailure(val failure: Failure):SideEffect