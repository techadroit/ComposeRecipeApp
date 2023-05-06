package com.feature.saved.recipes.state

import com.archerviewmodel.events.ArcherEvent
import com.domain.common.pojo.RecipeModel

sealed class SaveRecipeEvent : ArcherEvent
data class LoadRecipe(val loadAll: Boolean = true) : SaveRecipeEvent()

data class RemoveRecipe(val recipeModel: RecipeModel) : SaveRecipeEvent()
object RefreshViewEvent: SaveRecipeEvent()
