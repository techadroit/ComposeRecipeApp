package com.feature.saved.recipes.model

import com.domain.common.pojo.RecipeModel

data class SavedData(var allRecipes: List<RecipeModel>) {
    operator fun plus(recipeList: List<RecipeModel>): SavedData {
        val newList = (allRecipes + recipeList).distinctBy { it.id }
        return SavedData(newList)
    }
}
