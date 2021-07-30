package com.example.composerecipeapp.domain.usecases

import com.example.composerecipeapp.core.usecase.FlowUseCase
import com.example.composerecipeapp.data.repositories.RecipeLocalRepository
import com.example.composerecipeapp.data.repositories.RecipeRepository
import com.example.composerecipeapp.ui.pojo.RecipeModel
import com.example.composerecipeapp.util.NUMBER
import com.recipeapp.data.network.response.VideoListResponses
import com.skydoves.landscapist.offset


class SearchRecipeUsecase(
    var recipeRepository: RecipeRepository,
    var localRepository: RecipeLocalRepository
) :
    FlowUseCase<Pair<List<RecipeModel>, Boolean>, SearchRecipeUsecase.Param>() {
    override suspend fun run(params: Param): Pair<List<RecipeModel>, Boolean> {
        val response = recipeRepository.getRecipeForCuisine(
            params.cuisine,
            params.limitLicense, params.number, params.offset
        )
        val savedList = localRepository.getAllSavedRecipes().map { it.id }
        val recipeList = response.results.map {
            RecipeModel(
                it.id,
                it.title,
                it.servings,
                response.baseUri + it.image,
                it.readyInMinutes,
                isSaved = savedList.contains(it.id)
            )
        }
        val endOfList = params.number * params.offset >= response.totalResults

        return Pair(recipeList, endOfList)
    }

    data class Param(
        var limitLicense: Boolean = true,
        var cuisine: String,
        var number: Int = 10,
        var offset: Int = 0
    )
}


class SearchVideoRecipeUsecase(var recipeRepository: RecipeRepository) :
    FlowUseCase<VideoListResponses, SearchVideoRecipeUsecase.Param>() {
    override suspend fun run(params: Param): VideoListResponses {
        return recipeRepository.searchVideoRecipeFor(params.query, params.number, params.offset)
    }

    data class Param(var query: String, var number: Int = NUMBER, var offset: Int = 0)
}

