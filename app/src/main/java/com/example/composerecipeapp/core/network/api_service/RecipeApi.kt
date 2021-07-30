package com.recipeapp.core.network.api_service

import com.example.composerecipeapp.data.network.response.RecipeDetailResponse
import com.example.composerecipeapp.data.network.response.SearchKey
import com.recipeapp.data.network.response.RandomRecipesResponse
import com.recipeapp.data.network.response.RecipeSearchResponse
import com.recipeapp.data.network.response.SimilarRecipe
import com.recipeapp.data.network.response.VideoListResponses
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

val API_KEY = "95aed809c8d84dd6b831b9aaa35c5f24"

interface RecipeApi {

    @GET("recipes/random")
    suspend fun getRandomRecipes(
        @Query("limitLicense") limitLicense: Boolean,
        @Query("tags") tags: String,
        @Query("number") number: Int,
        @Query("apiKey") apiKey: String = API_KEY
    ): RandomRecipesResponse

    @GET("recipes/{id}/similar")
    suspend fun similarRecipes(
        @Path("id") id: String,
        @Query("limitLicense") limitLicense: Boolean,
        @Query("number") number: Int,
        @Query("apiKey") apiKey: String = API_KEY,
    ): List<SimilarRecipe>

    @GET("recipes/search")
    suspend fun searchRecipes(
        @Query("limitLicense") limitLicense: Boolean,
        @Query("query") tags: String,
        @Query("number") number: Int,
        @Query("apiKey") apiKey: String = API_KEY,
        @Query("offset") offset: Int = 0
    ): RecipeSearchResponse

    @GET("recipes/search")
    suspend fun searchRecipesWithCuisine(
        @Query("limitLicense") limitLicense: Boolean,
        @Query("cuisine") cuisine: String,
        @Query("number") number: Int,
        @Query("apiKey") apiKey: String = API_KEY,
        @Query("offset") offset: Int = 0
    ): RecipeSearchResponse

    @GET("food/videos/search")
    suspend fun searchVideos(
        @Query("query") tags: String,
        @Query("number") number: Int,
        @Query("apiKey") apiKey: String = API_KEY,
        @Query("offset") offset: Int = 0
    ): VideoListResponses

    @GET("recipes/autocomplete")
    suspend fun searchKeyword(
        @Query("query") tags: String,
        @Query("number") number: Int,
        @Query("apiKey") apiKey: String = API_KEY,
    ): List<SearchKey>

    @GET("recipes/{id}/information")
    suspend fun recipeDetail(
        @Path("id") id: String,
        @Query("includeNutrition") includeNutrition: Boolean = false,
        @Query("apiKey") apiKey: String = API_KEY
    ): RecipeDetailResponse
}
