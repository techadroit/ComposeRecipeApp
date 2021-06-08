package com.example.composerecipeapp.viewmodel.recipe_detail

import com.example.composerecipeapp.core.viewmodel.AppEvent


sealed class RecipeDetailEvent : AppEvent
data class LoadRecipeDetail(val id: String) : RecipeDetailEvent()
