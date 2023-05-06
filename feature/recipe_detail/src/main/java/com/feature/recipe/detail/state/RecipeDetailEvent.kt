package com.feature.recipe.detail.state

import com.archerviewmodel.events.ArcherEvent

sealed class RecipeDetailEvent : ArcherEvent
data class LoadRecipeDetail(val id: String) : RecipeDetailEvent()
object RemoveRecipe : RecipeDetailEvent()
object SaveRecipe : RecipeDetailEvent()
