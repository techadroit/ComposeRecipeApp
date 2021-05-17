package com.example.composerecipeapp.domain.usecases

import com.example.composerecipeapp.core.exception.Failure
import com.example.composerecipeapp.core.functional.Either
import com.example.composerecipeapp.core.usecase.UseCase
import com.example.composerecipeapp.data.network.response.RecipeDetailResponse
import com.example.composerecipeapp.data.repositories.RecipeRepository

class GetRecipeDetailUsecase(var recipeRepository: RecipeRepository) :
    UseCase<RecipeDetailResponse, GetRecipeDetailUsecase.Param>() {
    override suspend fun run(params: Param): Either<Failure, RecipeDetailResponse> {
        return recipeRepository.getRecipeDetailForId(params.id, params.includeNutrition)
    }

    data class Param(var includeNutrition: Boolean = true, var id: String)
}