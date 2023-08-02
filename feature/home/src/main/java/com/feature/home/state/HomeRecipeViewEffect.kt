package com.feature.home.state

import com.state_manager.side_effects.SideEffect

data class ViewAllViewEffect(val cuisine: String) : SideEffect
data class ViewRecipesDetailViewEffect(val recipeId: String) : SideEffect
data class LoadingError(val errorMsg:String) : SideEffect