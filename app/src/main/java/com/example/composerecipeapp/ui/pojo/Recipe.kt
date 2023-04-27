package com.example.composerecipeapp.ui.pojo

import com.data.repository.datasource.SavedRecipe
import com.data.repository.response.RecipeDetailResponse
import com.data.repository.response.RecipeSearchResponse
import com.data.repository.response.VideoListResponses

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
    val id: Int,
    val title: String,
    val sourceName: String,
    val sourceUrl: String,
    val imageUrl: String,
    val instructions: String,
    val servings: Int,
    val cookingTime: Int,
    val isSaved: Boolean = false
)

fun RecipeDetailModel.toRecipe() = RecipeModel(
    this.id, this.title, this.servings, this.imageUrl, this.cookingTime, this.isSaved
)

fun RecipeDetailResponse.toRecipeDetailModel(isSaved: Boolean = false) =
    RecipeDetailModel(
        id = this.id,
        servings = this.servings,
        cookingTime = 0,
        title = this.title,
        sourceName = this.sourceName,
        sourceUrl = this.sourceUrl,
        imageUrl = this.image,
        instructions = this.instructions,
        isSaved = isSaved
    )

fun SavedRecipe.toRecipeModal(isSaved: Boolean = false): RecipeModel {
    return RecipeModel(id, title, servings, imageUrl, readyInMinutes, isSaved)
}

fun VideoListResponses.toRecipeModel(): List<VideoRecipeModel> {
    return this.videos.map {
        VideoRecipeModel(it.rating, it.shortTitle, it.thumbnail, it.title, it.views, it.youTubeId)
    }
}

fun RecipeSearchResponse.toRecipeModel(isSaved:(Int)->Boolean): List<RecipeModel> =
    results.map {
        RecipeModel(
            it.id,
            it.title,
            it.servings,
            baseUri + it.image,
            it.readyInMinutes,
            isSaved = isSaved(it.id)
        )
    }

fun mapToRecipeEntity(recipeModel: RecipeModel) = SavedRecipe(
    recipeModel.id,
    recipeModel.imageUrl,
    recipeModel.cookingTime,
    recipeModel.servings,
    recipeModel.title
)
