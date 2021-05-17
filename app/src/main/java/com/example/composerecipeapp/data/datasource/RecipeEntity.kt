package com.example.composerecipeapp.data.datasource

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.composerecipeapp.ui.pojo.RecipeModel

@Entity(tableName = "saved_recipe")
class SavedRecipe(
    @PrimaryKey @ColumnInfo(name = "id")
    val id: Int,
    @ColumnInfo(name = "image")
    val imageUrl: String,
    @ColumnInfo(name = "cooking_time")
    val readyInMinutes: Int,
    @ColumnInfo(name = "servings")
    val servings: Int,
    @ColumnInfo(name = "title")
    val title: String
)

fun SavedRecipe.toRecipeModal(): RecipeModel {
    return RecipeModel(id, title, servings, imageUrl, readyInMinutes)
}