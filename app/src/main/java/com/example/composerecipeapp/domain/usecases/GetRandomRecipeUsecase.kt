package com.example.composerecipeapp.domain.usecases

import com.example.composerecipeapp.core.exception.Failure
import com.example.composerecipeapp.core.functional.Either
import com.example.composerecipeapp.core.usecase.UseCase
import com.example.composerecipeapp.data.network.response.RandomRecipesResponse
import com.example.composerecipeapp.data.repositories.RecipeRepository

class GetRandomRecipeUsecase(var recipeRepository: RecipeRepository) : UseCase<RandomRecipesResponse, GetRandomRecipeUsecase.Param>() {
    override suspend fun run(params: Param): Either<Failure, RandomRecipesResponse> {
        try {
            return recipeRepository.getRandomRecipe(params.limitLicense,params.tags,params.number)
//            return Either.Right(response)
        } catch (e: Exception) {
            return Either.Left(Failure.ServerError)
        }
    }

    data class Param(var limitLicense : Boolean = true,var tags : String,var number : Int = 10)
}