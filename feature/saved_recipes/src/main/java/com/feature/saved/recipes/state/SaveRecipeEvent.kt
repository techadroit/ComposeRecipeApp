package com.feature.saved.recipes.state

import com.state_manager.events.AppEvent
import com.domain.common.pojo.RecipeModel

sealed class SaveRecipeEvent : AppEvent
data class LoadRecipe(val loadAll: Boolean = true) : SaveRecipeEvent()

data class RemoveRecipe(val recipeModel: RecipeModel) : SaveRecipeEvent()
object RefreshViewEvent: SaveRecipeEvent()
