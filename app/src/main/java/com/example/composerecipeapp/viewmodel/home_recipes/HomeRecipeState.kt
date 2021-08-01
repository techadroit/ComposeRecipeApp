package com.example.composerecipeapp.viewmodel.home_recipes

import com.archerviewmodel.state.ArcherState
import com.example.composerecipeapp.core.functional.Consumable
import com.example.composerecipeapp.core.functional.ViewEffect
import com.example.composerecipeapp.domain.usecases.RecipeWithCuisine

data class HomeRecipeState(
    val list: List<RecipeWithCuisine> = emptyList(),
    val viewEffect: Consumable<ViewEffect>? = null
) : ArcherState

fun HomeRecipeState.onLoad(list: List<RecipeWithCuisine>) =
    copy(list = list)

data class ViewAllViewEffect(val cuisine: String) : ViewEffect()
data class ViewRecipesDetailViewEffect(val recipeId: String) : ViewEffect()
