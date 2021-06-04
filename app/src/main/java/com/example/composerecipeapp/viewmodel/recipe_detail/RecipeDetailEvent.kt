package com.example.composerecipeapp.viewmodel.recipe_detail

import com.example.composerecipeapp.core.viewmodel.AppEvent


interface RecipeDetailEvent : AppEvent
data class LoadRecipeDetail(val id: String) : RecipeDetailEvent
