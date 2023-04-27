package com.data.repository.repositories

import com.data.repository.datasource.RecipeDao
import com.data.repository.datasource.SavedRecipe
import kotlinx.coroutines.flow.Flow

class RecipeLocalRepository(var recipeDao: RecipeDao) {

    suspend fun getAllSavedRecipes(): List<SavedRecipe> = recipeDao.getAllSavedRecipe()

    suspend fun isSaved(recipeId: Int) = recipeDao.isSavedRecipe(recipeId) > 0

    fun getSavedRecipesCount(): Flow<Long> = recipeDao.getTotalSavedRecipes()

    suspend fun deleteRecipes(id: Int) = recipeDao.deleteRecipe(id)

    /**
     * Insert recipe to database
     * @return
     * row_number -> if the insert is sucessfull
     * -1 -> if the recipe already exist
     */
    suspend fun insertRecipe(recipe: SavedRecipe): Long = recipeDao.insertRecipe(recipe)
}
