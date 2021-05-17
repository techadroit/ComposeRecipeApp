package com.example.composerecipeapp.domain.usecases

import com.example.composerecipeapp.core.exception.Failure
import com.example.composerecipeapp.core.functional.Either
import com.example.composerecipeapp.core.usecase.UseCase
import com.example.composerecipeapp.data.network.response.RecipeSearchResponse
import com.example.composerecipeapp.data.network.response.VideoListResponses
import com.example.composerecipeapp.data.repositories.RecipeRepository
import com.example.composerecipeapp.util.NUMBER

class SearchRecipeUsecase(var recipeRepository: RecipeRepository) :
    UseCase<RecipeSearchResponse, SearchRecipeUsecase.Param>() {
    override suspend fun run(params: Param): Either<Failure, RecipeSearchResponse> {
        val either = recipeRepository.searchRecipeFor(
            params.query,
            params.limitLicense,
            params.number,
            params.offset
        )
        return either
    }

    data class Param(
        var limitLicense: Boolean = true,
        var query: String,
        var number: Int = 10,
        var offset: Int = 0
    )
}

class SearchVideoRecipeUsecase(var recipeRepository: RecipeRepository) :
    UseCase<VideoListResponses, SearchVideoRecipeUsecase.Param>() {
    override suspend fun run(params: Param): Either<Failure, VideoListResponses> {
        return recipeRepository.searchVideoRecipeFor(params.query, params.number, params.offset)
    }

    data class Param(var query: String, var number: Int = NUMBER, var offset: Int = 0)
}

