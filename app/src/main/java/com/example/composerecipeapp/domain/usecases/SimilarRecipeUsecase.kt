package com.example.composerecipeapp.domain.usecases

import com.example.composerecipeapp.core.usecase.FlowUseCase
import com.example.composerecipeapp.data.repositories.RecipeRepository
import com.example.composerecipeapp.ui.pojo.RecipeModel


class SimilarRecipeUsecase(var recipeRepository: RecipeRepository) :
    FlowUseCase<List<RecipeModel>, SimilarRecipeUsecase.Param>() {
    override suspend fun run(params: Param): List<RecipeModel> {
        val response =  recipeRepository.getSimilarRecipeFor(
            params.limitLicense,params.id, params.number)
        val recipeList = response.map{
            RecipeModel(
                it.id,
                it.title,
                it.servings,
                it.sourceUrl,
                it.readyInMinutes
            )
        }

        return recipeList
    }

    data class Param(
        var limitLicense: Boolean = true,
        var id: String,
        var number: Int = 10
    )
}

