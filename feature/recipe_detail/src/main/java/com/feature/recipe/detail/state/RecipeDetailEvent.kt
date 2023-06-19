package com.feature.recipe.detail.state

import com.state_manager.events.AppEvent

sealed class RecipeDetailEvent : AppEvent
data class LoadRecipeDetail(val id: String) : RecipeDetailEvent()
object RemoveRecipe : RecipeDetailEvent()
object SaveRecipe : RecipeDetailEvent()
