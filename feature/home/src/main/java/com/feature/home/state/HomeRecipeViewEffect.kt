package com.feature.home.state

import com.core.platform.functional.ViewEffect

data class ViewAllViewEffect(val cuisine: String) : ViewEffect()
data class ViewRecipesDetailViewEffect(val recipeId: String) : ViewEffect()
data class LoadingError(val errorMsg:String) : ViewEffect()