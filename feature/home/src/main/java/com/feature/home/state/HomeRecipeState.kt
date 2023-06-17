package com.feature.home.state

import com.archerviewmodel.state.ArcherState
import com.core.platform.functional.Consumable
import com.core.platform.functional.ViewEffect
import com.core.platform.functional.asConsumable
import com.domain.recipe.cuisines.RecipeWithCuisine

data class HomeRecipeState(
    val list: List<RecipeWithCuisine> = emptyList(),
    val viewEffect: Consumable<ViewEffect>? = null,
    val isLoadingPage: Boolean = true
) : ArcherState
fun HomeRecipeState.onLoad(list: List<RecipeWithCuisine>) =
    copy(list = list)

fun HomeRecipeState.add(items: RecipeWithCuisine) =
    copy(list = list.toMutableList().apply { add(items) })

fun HomeRecipeState.onViewEffect(viewEffect: ViewEffect) =
    copy(viewEffect = viewEffect.asConsumable())

fun HomeRecipeState.showLoading(isLoading:Boolean) = copy(isLoadingPage = isLoading)

data class ViewAllViewEffect(val cuisine: String) : ViewEffect()
data class ViewRecipesDetailViewEffect(val recipeId: String) : ViewEffect()
data class LoadingError(val errorMsg:String) : ViewEffect()
