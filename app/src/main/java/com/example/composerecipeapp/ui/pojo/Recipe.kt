package com.example.composerecipeapp.ui.pojo

data class RecipeModel(
    val id: Int,
    val title: String,
    val servings: Int,
    val imageUrl: String,
    val cookingTime: Int,
    val isSaved: Boolean = false
)

data class VideoRecipeModel(
    val rating: Double,
    val shortTitle: String,
    val thumbnail: String,
    val title: String,
    val views: Int,
    val youTubeId: String,
    val isSaved: Boolean = false
)

data class RecipeDetailModel(
    val title: String,
    val sourceName: String,
    val sourceUrl: String,
    val imageUrl: String,
    val instructions: String,
    val isSaved: Boolean = false
)
