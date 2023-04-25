package com.example.composerecipeapp.data.repositories

import com.core.platform.repository.BaseRepository
import com.example.composerecipeapp.core.network.api_service.NewRecipeApi
import com.example.composerecipeapp.data.network.response.*
import kotlinx.coroutines.flow.Flow

const val PAGE_ITEMS = 10

class NewRecipeRepository(val recipeApiService: NewRecipeApi) : BaseRepository {

     fun getRandomRecipe(
        limitLicense: Boolean,
        tags: String,
        number: Int = PAGE_ITEMS
    ): Flow<RandomRecipesResponse> =
            recipeApiService.getRandomRecipes(limitLicense, tags, number)

     fun getSimilarRecipeFor(
        limitLicense: Boolean,
        id: String,
        number: Int = PAGE_ITEMS
    ): Flow<List<SimilarRecipe>> =
            recipeApiService.similarRecipes(id = id, limitLicense = limitLicense, number = number)

     fun searchRecipeFor(
        query: String,
        limitLicense: Boolean,
        number: Int= PAGE_ITEMS,
        offset: Int = 0
    ): Flow<RecipeSearchResponse> =
            recipeApiService.searchRecipes(limitLicense, query, number, offset = offset)

     fun getRecipeForCuisine(
        cuisine: String,
        limitLicense: Boolean,
        number: Int= PAGE_ITEMS,
        offset: Int = 0
    ): Flow<RecipeSearchResponse> =
            recipeApiService.searchRecipesWithCuisine(limitLicense, cuisine, number, offset = offset)

     fun getRecipesForCuisine(
        cuisine: String,
        limitLicense: Boolean,
        number: Int= PAGE_ITEMS,
        offset: Int = 0
    ): Flow<RecipeSearchResponse> =
            recipeApiService.searchRecipesWithCuisine(limitLicense, cuisine, number, offset = offset)

     fun searchVideoRecipeFor(
        query: String,
        number: Int = PAGE_ITEMS,
        Offset: Int = 0
    ): Flow<VideoListResponses> =
            recipeApiService.searchVideos(tags = query, number = number, offset = Offset)

     fun searchKeyword(
        query: String,
        number: Int = PAGE_ITEMS
    ): Flow<List<SearchKey>> =
            recipeApiService.searchKeyword(tags = query, number = number)

     fun getRecipeDetailForId(
        id: String,
        includeNutrition: Boolean
    ): Flow<RecipeDetailResponse> =
            recipeApiService.recipeDetail(id = id, includeNutrition = includeNutrition)

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

