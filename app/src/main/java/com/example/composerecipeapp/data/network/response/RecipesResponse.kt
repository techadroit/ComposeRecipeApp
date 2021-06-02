package com.recipeapp.data.network.response

import com.example.composerecipeapp.data.network.response.Ingredient
import com.example.composerecipeapp.ui.pojo.VideoRecipeModel

data class RandomRecipesResponse(
    val recipes: List<Recipe>
)

data class Recipe(
    val aggregateLikes: Int,
    val analyzedInstructions: List<AnalyzedInstruction>,
    val cheap: Boolean,
    val creditsText: String,
    val cuisines: List<Any>,
    val dairyFree: Boolean,
    val diets: List<String>,
    val dishTypes: List<String>,
    val extendedIngredients: List<ExtendedIngredient>,
    val gaps: String,
    val glutenFree: Boolean,
    val healthScore: Double,
    val id: Int,
    val image: String,
    val imageType: String,
    val instructions: String,
    val ketogenic: Boolean,
    val license: String,
    val lowFodmap: Boolean,
    val occasions: List<String>,
    val pricePerServing: Double,
    val readyInMinutes: Int,
    val servings: Int,
    val sourceName: String,
    val sourceUrl: String,
    val spoonacularScore: Double,
    val spoonacularSourceUrl: String,
    val sustainable: Boolean,
    val title: String,
    val vegan: Boolean,
    val vegetarian: Boolean,
    val veryHealthy: Boolean,
    val veryPopular: Boolean,
    val weightWatcherSmartPoints: Int,
    val whole30: Boolean,
    val winePairing: WinePairing
)

data class AnalyzedInstruction(
    val name: String,
    val steps: List<Step>
)

data class Step(
    val equipment: List<Any>,
    val ingredients: List<Ingredient>,
    val length: Length,
    val number: Int,
    val step: String
)

data class Length(
    val number: Int,
    val unit: String
)

data class ExtendedIngredient(
    val aisle: String,
    val amount: Double,
    val consitency: String,
    val id: Int,
    val image: String,
    val measures: Measures,
    val meta: List<String>,
    val metaInformation: List<String>,
    val name: String,
    val original: String,
    val originalName: String,
    val originalString: String,
    val unit: String
)

data class Measures(
    val metric: Metric,
    val us: Us
)

data class Metric(
    val amount: Double,
    val unitLong: String,
    val unitShort: String
)

data class Us(
    val amount: Double,
    val unitLong: String,
    val unitShort: String
)

data class WinePairing(
    val pairedWines: List<String>,
    val pairingText: String,
    val productMatches: List<WinePairedProduct>
)

data class WinePairedProduct(
    val averageRating: Double,
    val description: Any,
    val id: Int,
    val imageUrl: String,
    val link: String,
    val price: String,
    val ratingCount: Double,
    val score: Double,
    val title: String
)

data class RecipeSearchResponse(
    val number: Int,
    val offset: Int,
    val results: List<Result>,
    val totalResults: Int,
    val baseUri: String
)

data class SimilarRecipe(
    val id: Int,
    val imageType: String,
    val sourceUrl: String,
    val readyInMinutes: Int,
    val servings: Int,
    val title: String
)

data class Result(
    val id: Int,
    val image: String,
    val imageUrls: List<String>,
    val readyInMinutes: Int,
    val servings: Int,
    val title: String
)

data class VideoListResponses(
    val totalResults: Int,
    val videos: List<Video>
)

data class Video(
    val length: Int,
    val rating: Double,
    val shortTitle: String,
    val thumbnail: String,
    val title: String,
    val views: Int,
    val youTubeId: String
)

fun VideoListResponses.toRecipeModel() : List<VideoRecipeModel>{
    return this.videos.map {
        VideoRecipeModel(it.rating,it.shortTitle,it.thumbnail,it.title,it.views,it.youTubeId)
    }
}
