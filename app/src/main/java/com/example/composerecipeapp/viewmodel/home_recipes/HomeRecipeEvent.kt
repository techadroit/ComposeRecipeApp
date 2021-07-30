package com.example.composerecipeapp.viewmodel.home_recipes

import com.archerviewmodel.events.ArcherEvent

sealed class HomeRecipeEvent : ArcherEvent

object LoadRecipeEvent : HomeRecipeEvent()

data class ViewAllRecipes(val cuisine: String) : HomeRecipeEvent()

data class ViewRecipeDetail(val recipeId: String) : HomeRecipeEvent()
