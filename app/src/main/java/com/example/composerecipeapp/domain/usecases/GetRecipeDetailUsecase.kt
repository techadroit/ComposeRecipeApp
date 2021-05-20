package com.example.composerecipeapp.domain.usecases

import com.example.composerecipeapp.core.usecase.FlowUseCase
import com.example.composerecipeapp.data.repositories.RecipeRepository
import com.example.composerecipeapp.data.network.response.RecipeDetailResponse

class GetRecipeDetailUsecase(var recipeRepository: RecipeRepository) :
    FlowUseCase<RecipeDetailResponse, GetRecipeDetailUsecase.Param>() {
    override suspend fun run(params: Param): RecipeDetailResponse {
        return recipeRepository.getRecipeDetailForId(params.id, params.includeNutrition)
    }

    data class Param(var includeNutrition: Boolean = true, var id: String)
}
