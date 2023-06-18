package com.feature.home.state

import com.state_manager.state.ArcherState
import com.core.platform.functional.Consumable
import com.core.platform.functional.ViewEffect
import com.core.platform.functional.asConsumable
import com.domain.recipe.cuisines.RecipeWithCuisine

data class HomeRecipeState(
    val list: List<RecipeWithCuisine> = emptyList(),
    val viewEffect: Consumable<ViewEffect>? = null,
    val isLoadingPage: Boolean = true
) : ArcherState

fun HomeRecipeState.initialState() = HomeRecipeState()
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