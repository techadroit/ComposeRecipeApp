package com.example.composerecipeapp.domain.usecases

import com.example.composerecipeapp.core.usecase.FlowUseCase
import com.example.composerecipeapp.data.network.response.toRecipeDetailModel
import com.example.composerecipeapp.data.repositories.RecipeLocalRepository
import com.example.composerecipeapp.data.repositories.RecipeRepository
import com.example.composerecipeapp.ui.pojo.RecipeDetailModel

class GetRecipeDetailUsecase(
    var recipeRepository: RecipeRepository,
    val localRepository: RecipeLocalRepository
) :
    FlowUseCase<RecipeDetailModel, GetRecipeDetailUsecase.Param>() {
    override suspend fun run(params: Param): RecipeDetailModel {
        val response = recipeRepository.getRecipeDetailForId(params.id, params.includeNutrition)
        val isSaved = localRepository.isSaved(response.id)
        return response.toRecipeDetailModel(isSaved = isSaved)
    }

    data class Param(var includeNutrition: Boolean = true, var id: String)
}
