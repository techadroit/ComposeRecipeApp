package com.data.repository

import com.data.repository.response.*
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface NewRecipeApi {

    @GET("recipes/random")
    fun getRandomRecipes(
        @Query("limitLicense") limitLicense: Boolean,
        @Query("tags") tags: String,
        @Query("number") number: Int,
        @Query("apiKey") apiKey: String
    ): Flow<RandomRecipesResponse>

    @GET("recipes/{id}/similar")
    fun similarRecipes(
        @Path("id") id: String,
        @Query("limitLicense") limitLicense: Boolean,
        @Query("number") number: Int,
        @Query("apiKey") apiKey: String,
    ): Flow<List<SimilarRecipe>>

    @GET("recipes/search")
    fun searchRecipes(
        @Query("limitLicense") limitLicense: Boolean,
        @Query("query") tags: String,
        @Query("number") number: Int,
        @Query("apiKey") apiKey: String,
        @Query("offset") offset: Int = 0
    ): Flow<RecipeSearchResponse>

    @GET("recipes/search")
    fun searchRecipesWithCuisine(
        @Query("limitLicense") limitLicense: Boolean,
        @Query("cuisine") cuisine: String,
        @Query("number") number: Int,
        @Query("apiKey") apiKey: String,
        @Query("offset") offset: Int = 0
    ): Flow<RecipeSearchResponse>

    @GET("food/videos/search")
    fun searchVideos(
        @Query("query") tags: String,
        @Query("number") number: Int,
        @Query("apiKey") apiKey: String,
        @Query("offset") offset: Int = 0
    ): Flow<VideoListResponses>

    @GET("recipes/autocomplete")
    fun searchKeyword(
        @Query("query") tags: String,
        @Query("number") number: Int,
        @Query("apiKey") apiKey: String,
    ): Flow<List<SearchKey>>

    @GET("recipes/{id}/information")
    fun recipeDetail(
        @Path("id") id: String,
        @Query("includeNutrition") includeNutrition: Boolean = false,
        @Query("apiKey") apiKey: String
    ): Flow<RecipeDetailResponse>
}

