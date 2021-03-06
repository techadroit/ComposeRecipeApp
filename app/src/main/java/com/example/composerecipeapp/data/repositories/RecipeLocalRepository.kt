package com.example.composerecipeapp.data.repositories

import com.example.composerecipeapp.core.repository.BaseRepository
import com.example.composerecipeapp.data.datasource.RecipeDao
import com.example.composerecipeapp.data.datasource.SavedRecipe
import com.example.composerecipeapp.ui.pojo.RecipeModel
import kotlinx.coroutines.flow.Flow

class RecipeLocalRepository(var recipeDao: RecipeDao) : BaseRepository {

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
    suspend fun insertRecipe(recipe: SavedRecipe): Long =
        recipeDao.insertRecipe(recipe)
}

fun mapToRecipeEntity(recipeModel: RecipeModel) = SavedRecipe(
    recipeModel.id,
    recipeModel.imageUrl,
    recipeModel.cookingTime,
    recipeModel.servings,
    recipeModel.title
)
