package com.example.composerecipeapp.core.network.api_service

import com.example.composerecipeapp.data.network.response.RecipeDetailResponse
import com.example.composerecipeapp.data.network.response.SearchKey
import com.example.composerecipeapp.data.network.response.RandomRecipesResponse
import com.example.composerecipeapp.data.network.response.RecipeSearchResponse
import com.example.composerecipeapp.data.network.response.SimilarRecipe
import com.example.composerecipeapp.data.network.response.VideoListResponses
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
        @Query("apiKey") apiKey: String = API_KEY
    ): Flow<RandomRecipesResponse>

    @GET("recipes/{id}/similar")
     fun similarRecipes(
        @Path("id") id: String,
        @Query("limitLicense") limitLicense: Boolean,
        @Query("number") number: Int,
        @Query("apiKey") apiKey: String = API_KEY,
    ): Flow<List<SimilarRecipe>>

    @GET("recipes/search")
     fun searchRecipes(
        @Query("limitLicense") limitLicense: Boolean,
        @Query("query") tags: String,
        @Query("number") number: Int,
        @Query("apiKey") apiKey: String = API_KEY,
        @Query("offset") offset: Int = 0
    ): Flow<RecipeSearchResponse>

    @GET("recipes/search")
     fun searchRecipesWithCuisine(
        @Query("limitLicense") limitLicense: Boolean,
        @Query("cuisine") cuisine: String,
        @Query("number") number: Int,
        @Query("apiKey") apiKey: String = API_KEY,
        @Query("offset") offset: Int = 0
    ): Flow<RecipeSearchResponse>

    @GET("food/videos/search")
     fun searchVideos(
        @Query("query") tags: String,
        @Query("number") number: Int,
        @Query("apiKey") apiKey: String = API_KEY,
        @Query("offset") offset: Int = 0
    ): Flow<VideoListResponses>

    @GET("recipes/autocomplete")
     fun searchKeyword(
        @Query("query") tags: String,
        @Query("number") number: Int,
        @Query("apiKey") apiKey: String = API_KEY,
    ): Flow<List<SearchKey>>

    @GET("recipes/{id}/information")
     fun recipeDetail(
        @Path("id") id: String,
        @Query("includeNutrition") includeNutrition: Boolean = false,
        @Query("apiKey") apiKey: String = API_KEY
    ): Flow<RecipeDetailResponse>
}

