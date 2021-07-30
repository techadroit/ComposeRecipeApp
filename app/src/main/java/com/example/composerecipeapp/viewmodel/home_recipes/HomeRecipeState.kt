package com.example.composerecipeapp.viewmodel.home_recipes

import com.archerviewmodel.state.ArcherState
import com.example.composerecipeapp.core.functional.Consumable
import com.example.composerecipeapp.domain.usecases.RecipeWithCuisine

data class HomeRecipeState(
    val list: List<RecipeWithCuisine> = emptyList(),
    val sideEffect: Consumable<SideEffect>? = null
) : ArcherState

fun HomeRecipeState.onLoad(list: List<RecipeWithCuisine>) =
    copy(list = list)

sealed class SideEffect {
    data class ViewAllSideEffect(val cuisine: String) : SideEffect()
    data class ViewRecipesDetailSideEffect(val recipeId: String) : SideEffect()
}
