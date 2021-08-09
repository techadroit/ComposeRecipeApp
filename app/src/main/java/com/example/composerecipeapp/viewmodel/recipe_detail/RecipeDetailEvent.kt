package com.example.composerecipeapp.viewmodel.recipe_detail

import com.archerviewmodel.events.ArcherEvent

sealed class RecipeDetailEvent : ArcherEvent
data class LoadRecipeDetail(val id: String) : RecipeDetailEvent()
object RemoveRecipe : RecipeDetailEvent()
object SaveRecipe : RecipeDetailEvent()
