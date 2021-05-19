package com.example.composerecipeapp.data.repositories

import com.example.composerecipeapp.core.exception.Failure
import com.example.composerecipeapp.core.repository.BaseRepository
import com.recipeapp.core.network.api_service.RecipeApi
import com.recipeapp.data.network.response.RandomRecipesResponse
import com.recipeapp.data.network.response.RecipeDetailResponse
import com.recipeapp.data.network.response.RecipeSearchResponse
import com.recipeapp.data.network.response.VideoListResponses
import java.io.IOException

class RecipeRepository(val recipeApiService: RecipeApi) : BaseRepository {

    suspend fun getRandomRecipe(
        limitLicense: Boolean,
        tags: String,
        number: Int
    ): RandomRecipesResponse =
        run {
            recipeApiService.getRandomRecipes(limitLicense, tags, number)
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

    suspend fun <T> run(invoker: suspend () -> T): T {
        try {
            return invoker.invoke()
        } catch (e: Exception) {
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

    suspend fun getRecipeDetailForId(
        id: String,
        includeNutrition: Boolean
    ): RecipeDetailResponse =
        run {
            recipeApiService.recipeDetail(id = id, includeNutrition = includeNutrition)
        }

}
