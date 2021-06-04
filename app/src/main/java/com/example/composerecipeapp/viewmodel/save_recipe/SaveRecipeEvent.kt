package com.example.composerecipeapp.viewmodel.save_recipe

import com.example.composerecipeapp.core.viewmodel.AppEvent

interface SaveRecipeEvent : AppEvent
data class LoadRecipe(val loadAll: Boolean = true) : SaveRecipeEvent

