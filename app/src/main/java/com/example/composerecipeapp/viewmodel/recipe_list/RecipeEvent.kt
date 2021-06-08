package com.example.composerecipeapp.viewmodel.recipe_list

import com.example.composerecipeapp.core.viewmodel.AppEvent
import com.example.composerecipeapp.ui.pojo.RecipeModel


sealed class RecipeEvent : AppEvent
data class LoadRecipes(val query: String, var isPaginate: Boolean = false) :
    RecipeEvent()

data class SaveRecipeEvent(val recipeModel: RecipeModel) : RecipeEvent()

data class RemoveSavedRecipeEvent(val recipeModel: RecipeModel) : RecipeEvent()

