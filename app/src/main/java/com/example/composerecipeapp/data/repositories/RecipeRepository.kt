package com.example.composerecipeapp.data.repositories

import com.example.composerecipeapp.core.exception.Failure
import com.example.composerecipeapp.core.network.api_service.RecipeApi
import com.example.composerecipeapp.core.repository.BaseRepository
import com.example.composerecipeapp.data.network.response.RecipeDetailResponse
import com.example.composerecipeapp.data.network.response.SearchKey
import com.recipeapp.data.network.response.RandomRecipesResponse
import com.recipeapp.data.network.response.RecipeSearchResponse
import com.recipeapp.data.network.response.SimilarRecipe
import com.recipeapp.data.network.response.VideoListResponses

class RecipeRepository(val recipeApiService: RecipeApi) : BaseRepository {

    suspend fun getRandomRecipe(
        limitLicense: Boolean,
        tags: String,
        number: Int
    ): RandomRecipesResponse =
        run {
            recipeApiService.getRandomRecipes(limitLicense, tags, number)
        }

    suspend fun getSimilarRecipeFor(
        limitLicense: Boolean,
        id: String,
        number: Int
    ): List<SimilarRecipe> =
        run {
            recipeApiService.similarRecipes(id = id, limitLicense = limitLicense, number = number)
        }

    suspend fun searchRecipeFor(
        query: String,
        limitLicense: Boolean,
        number: Int,
        offset: Int = 0
    ): RecipeSearchResponse =
        run {
            recipeApiService.searchRecipes(limitLicense, query, number, offset = offset)
        }

    suspend fun getRecipeForCuisine(
        cuisine: String,
        limitLicense: Boolean,
        number: Int,
        offset: Int = 0
    ): RecipeSearchResponse =
        run {
            recipeApiService.searchRecipesWithCuisine(limitLicense, cuisine, number, offset = offset)
        }

    suspend fun <T> run(invoker: suspend () -> T): T {
        try {
            return invoker.invoke()
        } catch (e: Exception) {
            e.printStackTrace()
            throw Failure.ServerError
        }
    }

    suspend fun searchVideoRecipeFor(
        query: String,
        number: Int,
        Offset: Int = 0
    ): VideoListResponses =
        run {
            recipeApiService.searchVideos(tags = query, number = number, offset = Offset)
        }

    suspend fun searchKeyword(
        query: String,
        number: Int
    ): List<SearchKey> =
        run {
            recipeApiService.searchKeyword(tags = query, number = number)
        }

    suspend fun getRecipeDetailForId(
        id: String,
        includeNutrition: Boolean
    ): RecipeDetailResponse =
        run {
            recipeApiService.recipeDetail(id = id, includeNutrition = includeNutrition)
        }

    fun getSupportedCuisine(): List<String> = listOf(
        "American",
        "British",
        "Chinese",
        "European",
        "French",
        "Indian",
        "Italian",
        "Irish",
        "Japanese",
        "Mediterranean",
        "Spanish",
        "Thai"
    )
}
