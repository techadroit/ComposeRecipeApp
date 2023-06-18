package com.feature.home.state

import com.state_manager.events.AppEvent

sealed class HomeRecipeEvent : AppEvent

object LoadRecipeEvent : HomeRecipeEvent()
object RefreshHomeEvent : HomeRecipeEvent()

data class ViewAllRecipes(val cuisine: String) : HomeRecipeEvent()

data class ViewRecipeDetail(val recipeId: String) : HomeRecipeEvent()
