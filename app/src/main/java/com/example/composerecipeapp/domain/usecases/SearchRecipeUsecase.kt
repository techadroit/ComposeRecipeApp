package com.example.composerecipeapp.domain.usecases

import com.example.composerecipeapp.core.usecase.FlowUseCase
import com.example.composerecipeapp.data.repositories.RecipeRepository
import com.example.composerecipeapp.ui.pojo.RecipeModel
import com.example.composerecipeapp.util.NUMBER
import com.recipeapp.data.network.response.VideoListResponses


class SearchRecipeUsecase(var recipeRepository: RecipeRepository) :
    FlowUseCase<List<RecipeModel>, SearchRecipeUsecase.Param>() {
    override suspend fun run(params: Param): List<RecipeModel> {
        val response =  recipeRepository.searchRecipeFor(params.query,
            params.limitLicense, params.number,params.offset)
        val recipeList = response.results.map{
            RecipeModel(
                it.id,
                it.title,
                it.servings,
                response.baseUri + it.image,
                it.readyInMinutes
            )
        }
        return recipeList
    }

    data class Param(
        var limitLicense: Boolean = true,
        var query: String,
        var number: Int = 10,
        var offset: Int = 0
    )
}



class SearchVideoRecipeUsecase(var recipeRepository: RecipeRepository) :
    FlowUseCase<VideoListResponses, SearchVideoRecipeUsecase.Param>() {
    override suspend fun run(params: Param): VideoListResponses {
        return recipeRepository.searchVideoRecipeFor(params.query, params.number,params.offset)
    }

    data class Param(var query: String, var number: Int = NUMBER,var offset : Int = 0)
}

