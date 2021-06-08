package com.example.composerecipeapp.viewmodel.save_recipe

import com.example.composerecipeapp.core.viewmodel.AppEvent
import com.example.composerecipeapp.ui.pojo.RecipeModel

sealed class SaveRecipeEvent : AppEvent
data class LoadRecipe(val loadAll: Boolean = true) : SaveRecipeEvent()

data class RemoveRecipe(val recipeModel: RecipeModel) : SaveRecipeEvent()

