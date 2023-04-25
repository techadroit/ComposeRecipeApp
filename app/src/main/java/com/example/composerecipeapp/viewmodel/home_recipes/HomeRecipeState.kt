package com.example.composerecipeapp.viewmodel.home_recipes

import com.archerviewmodel.state.ArcherState
import com.core.platform.functional.Consumable
import com.core.platform.functional.ViewEffect
import com.example.composerecipeapp.domain.usecases.RecipeWithCuisine

data class HomeRecipeState(
    val list: List<RecipeWithCuisine> = emptyList(),
    val viewEffect: Consumable<ViewEffect>? = null
) : ArcherState

fun HomeRecipeState.onLoad(list: List<RecipeWithCuisine>) =
    copy(list = list)

fun HomeRecipeState.add(items: RecipeWithCuisine) =
    copy(list = list.toMutableList().apply { add(items) })

data class ViewAllViewEffect(val cuisine: String) : ViewEffect()
data class ViewRecipesDetailViewEffect(val recipeId: String) : ViewEffect()
