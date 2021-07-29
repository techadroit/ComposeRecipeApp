package com.example.composerecipeapp.viewmodel.home_recipes

import com.archerviewmodel.state.ArcherState
import com.example.composerecipeapp.domain.usecases.RecipeWithCuisine

data class HomeRecipeState(val list: List<RecipeWithCuisine> = emptyList()) : ArcherState

fun HomeRecipeState.onLoad(list: List<RecipeWithCuisine>) =
    copy(list = list)

