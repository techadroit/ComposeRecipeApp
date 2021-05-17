package com.example.composerecipeapp.data.repositories

import com.example.composerecipeapp.core.exception.Failure
import com.example.composerecipeapp.core.functional.Either
import com.example.composerecipeapp.core.network.api_service.RecipeApi
import com.example.composerecipeapp.core.repository.BaseRepository
import com.example.composerecipeapp.data.network.response.RandomRecipesResponse
import com.example.composerecipeapp.data.network.response.RecipeDetailResponse
import com.example.composerecipeapp.data.network.response.RecipeSearchResponse
import com.example.composerecipeapp.data.network.response.VideoListResponses

class RecipeRepository(val recipeApiService: RecipeApi) : BaseRepository {

    suspend fun getRandomRecipe(
        limitLicense: Boolean,
        tags: String,
        number: Int
    ): Either<Failure, RandomRecipesResponse> =
        try {
            val response = recipeApiService.getRandomRecipes(limitLicense, tags, number)
            Either.Right(response)
        } catch (e: Exception) {
            Either.Left(Failure.ServerError)
        }

    suspend fun searchRecipeFor(
        query: String,
        limitLicense: Boolean,
        number: Int,
        offset : Int = 0
    ): Either<Failure, RecipeSearchResponse> =
        try {
            val response = recipeApiService.searchRecipes(limitLicense, query, number,offset = offset)
            Either.Right(response)
        } catch (e: Exception) {
            Either.Left(Failure.ServerError)
        }

    suspend fun searchVideoRecipeFor(
        query: String,
        number: Int,
        Offset : Int = 0
    ): Either<Failure, VideoListResponses> =
        try {
            val response = recipeApiService.searchVideos(tags = query,number =  number,offset = Offset)
            Either.Right(response)
        } catch (e: Exception) {
            Either.Left(Failure.ServerError)
        }

    suspend fun getRecipeDetailForId(id: String,includeNutrition:Boolean): Either<Failure, RecipeDetailResponse> =
        try {
            val response = recipeApiService.recipeDetail(id = id,includeNutrition = includeNutrition)
            Either.Right(response)
        } catch (e: Exception) {
            Either.Left(Failure.ServerError)
        }

}