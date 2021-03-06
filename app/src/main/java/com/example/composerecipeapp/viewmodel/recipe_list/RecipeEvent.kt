package com.example.composerecipeapp.viewmodel.recipe_list

import com.archerviewmodel.events.ArcherEvent
import com.example.composerecipeapp.ui.pojo.RecipeModel

sealed class RecipeEvent : ArcherEvent
data class LoadRecipes(val query: String, var isPaginate: Boolean = false) :
    RecipeEvent()

data class SaveRecipeEvent(val recipeModel: RecipeModel) : RecipeEvent()

data class RemoveSavedRecipeEvent(val recipeModel: RecipeModel) : RecipeEvent()
