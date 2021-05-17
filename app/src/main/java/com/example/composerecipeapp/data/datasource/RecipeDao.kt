package com.example.composerecipeapp.data.datasource

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged

@Dao
abstract class RecipeDao {

    @Query("SELECT * from saved_recipe ORDER BY id ASC")
    suspend abstract fun getAllSavedRecipe(): List<SavedRecipe>

    @Query("SELECT count(id) from saved_recipe")
    abstract fun getSavedRecipesCount(): Flow<Long>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend abstract fun insertRecipe(recipe: SavedRecipe): Long

    fun getTotalSavedRecipes(): Flow<Long> = getSavedRecipesCount().distinctUntilChanged()
}