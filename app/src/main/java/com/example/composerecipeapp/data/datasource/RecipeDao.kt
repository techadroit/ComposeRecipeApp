package com.example.composerecipeapp.data.datasource

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged

@Dao
abstract class RecipeDao {

    @Query("SELECT * from saved_recipe ORDER BY id ASC")
    abstract suspend fun getAllSavedRecipe(): List<SavedRecipe>

    @Query("SELECT count(id) from saved_recipe where id = :id")
    abstract suspend fun isSavedRecipe(id: Int): Int

    @Query("SELECT count(id) from saved_recipe")
    abstract fun getSavedRecipesCount(): Flow<Long>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract suspend fun insertRecipe(recipe: SavedRecipe): Long

    @Query("DELETE FROM saved_recipe WHERE id = :id")
    abstract suspend fun deleteRecipe(id: Int): Int

    fun getTotalSavedRecipes(): Flow<Long> = getSavedRecipesCount().distinctUntilChanged()
}
