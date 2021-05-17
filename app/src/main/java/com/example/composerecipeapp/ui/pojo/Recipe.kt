package com.example.composerecipeapp.ui.pojo

data class RecipeModel(
    val id: Int,
    val title: String,
    val servings: Int,
    val imageUrl: String,
    val cookingTime: Int
)

data class VideoRecipeModel(
    val rating: Double,
    val shortTitle: String,
    val thumbnail: String,
    val title: String,
    val views: Int,
    val youTubeId: String
)
