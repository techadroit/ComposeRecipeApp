package com.data.repository.repositories

import com.data.repository.NewRecipeApi
import com.data.repository.response.*
import kotlinx.coroutines.flow.Flow

const val PAGE_ITEMS = 10
val API_KEY = "95aed809c8d84dd6b831b9aaa35c5f24"

class NewRecipeRepository(val recipeApiService: NewRecipeApi) {

    fun getRandomRecipe(
        limitLicense: Boolean,
        tags: String,
        number: Int = PAGE_ITEMS,
    ): Flow<RandomRecipesResponse> =
        recipeApiService.getRandomRecipes(limitLicense, tags, number, apiKey = API_KEY)

    fun getSimilarRecipeFor(
        limitLicense: Boolean,
        id: String,
        number: Int = PAGE_ITEMS
    ): Flow<List<SimilarRecipe>> =
        recipeApiService.similarRecipes(
            id = id,
            limitLicense = limitLicense,
            number = number,
            apiKey = API_KEY
        )

    fun searchRecipeFor(
        query: String,
        limitLicense: Boolean,
        number: Int = PAGE_ITEMS,
        offset: Int = 0
    ): Flow<RecipeSearchResponse> =
        recipeApiService.searchRecipes(
            limitLicense,
            query,
            number,
            offset = offset,
            apiKey = API_KEY
        )

    fun getRecipeForCuisine(
        cuisine: String,
        limitLicense: Boolean,
        number: Int = PAGE_ITEMS,
        offset: Int = 0
    ): Flow<RecipeSearchResponse> =
        recipeApiService.searchRecipesWithCuisine(
            limitLicense,
            cuisine,
            number,
            offset = offset,
            apiKey = API_KEY
        )

    fun getRecipesForCuisine(
        cuisine: String,
        limitLicense: Boolean,
        number: Int = PAGE_ITEMS,
        offset: Int = 0
    ): Flow<RecipeSearchResponse> =
        recipeApiService.searchRecipesWithCuisine(
            limitLicense,
            cuisine,
            number,
            offset = offset,
            apiKey = API_KEY
        )

    fun searchVideoRecipeFor(
        query: String,
        number: Int = PAGE_ITEMS,
        Offset: Int = 0
    ): Flow<VideoListResponses> =
        recipeApiService.searchVideos(
            tags = query,
            number = number,
            offset = Offset,
            apiKey = API_KEY
        )

    fun searchKeyword(
        query: String,
        number: Int = PAGE_ITEMS
    ): Flow<List<SearchKey>> =
        recipeApiService.searchKeyword(tags = query, number = number, apiKey = API_KEY)

    fun getRecipeDetailForId(
        id: String,
        includeNutrition: Boolean
    ): Flow<RecipeDetailResponse> =
        recipeApiService.recipeDetail(
            id = id,
            includeNutrition = includeNutrition,
            apiKey = API_KEY
        )

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

