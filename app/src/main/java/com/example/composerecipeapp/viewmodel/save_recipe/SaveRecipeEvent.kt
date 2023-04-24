package com.example.composerecipeapp.viewmodel.save_recipe

import com.archerviewmodel.events.ArcherEvent
import com.example.composerecipeapp.ui.pojo.RecipeModel

sealed class SaveRecipeEvent : ArcherEvent
data class LoadRecipe(val loadAll: Boolean = true) : SaveRecipeEvent()

data class RemoveRecipe(val recipeModel: RecipeModel) : SaveRecipeEvent()
object RefreshViewEvent: SaveRecipeEvent()
