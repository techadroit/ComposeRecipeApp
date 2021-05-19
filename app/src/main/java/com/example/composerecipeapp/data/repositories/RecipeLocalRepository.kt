package com.example.composerecipeapp.data.repositories

import com.example.composerecipeapp.core.exception.NoSavedRecipe
import com.example.composerecipeapp.core.repository.BaseRepository
import com.example.composerecipeapp.data.datasource.RecipeDao
import com.example.composerecipeapp.data.datasource.SavedRecipe
import com.example.composerecipeapp.ui.pojo.RecipeModel
import kotlinx.coroutines.flow.Flow

class RecipeLocalRepository(var recipeDao: RecipeDao) : BaseRepository {

    suspend fun getAllSavedRecipes(): List<SavedRecipe> = recipeDao.getAllSavedRecipe().ifEmpty {
        throw NoSavedRecipe
    }


    fun getSavedRecipesCount(): Flow<Long> = recipeDao.getTotalSavedRecipes()

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
