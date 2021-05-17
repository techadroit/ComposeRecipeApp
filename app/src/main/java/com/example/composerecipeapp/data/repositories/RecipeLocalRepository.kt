package com.example.composerecipeapp.data.repositories

import com.example.composerecipeapp.core.exception.Failure
import com.example.composerecipeapp.core.exception.NoSavedRecipe
import com.example.composerecipeapp.core.functional.Either
import com.example.composerecipeapp.core.repository.BaseRepository
import com.example.composerecipeapp.data.datasource.RecipeDao
import com.example.composerecipeapp.data.datasource.SavedRecipe
import com.example.composerecipeapp.ui.pojo.RecipeModel
import kotlinx.coroutines.flow.Flow

class RecipeLocalRepository(var recipeDao: RecipeDao) : BaseRepository {

    suspend fun getAllSavedRecipes(): Either<Failure, List<SavedRecipe>> {
        val list = recipeDao.getAllSavedRecipe()
        if (list.size == 0) {
            return Either.Left(NoSavedRecipe)
        } else {
            return Either.Right(list)
        }
    }

    fun getSavedReicpesCount(): Flow<Long> = recipeDao.getTotalSavedRecipes()
    /**
     * Insert recipe to database
     * @return
     * row_number -> if the insert is sucessfull
     * -1 -> if the recipe already exist
     */
    suspend fun insertRecipe(recipe: SavedRecipe): Either<Failure, Long> {
        val response = recipeDao.insertRecipe(recipe)
        return Either.Right(response)
    }
}

fun mapToRecipeEntity(recipeModel: RecipeModel) = SavedRecipe(
    recipeModel.id,
    recipeModel.imageUrl,
    recipeModel.cookingTime,
    recipeModel.servings,
    recipeModel.title
)
