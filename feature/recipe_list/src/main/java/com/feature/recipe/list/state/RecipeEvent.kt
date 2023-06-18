package com.feature.recipe.list.state

import com.state_manager.events.AppEvent
import com.domain.common.pojo.RecipeModel

sealed class RecipeEvent : AppEvent
data class LoadRecipes(val query: String, var isPaginate: Boolean = false) :
    RecipeEvent()
 object RefreshRecipeList : RecipeEvent()

data class SaveRecipeEvent(val recipeModel: RecipeModel) : RecipeEvent()

data class RemoveSavedRecipeEvent(val recipeModel: RecipeModel) : RecipeEvent()
