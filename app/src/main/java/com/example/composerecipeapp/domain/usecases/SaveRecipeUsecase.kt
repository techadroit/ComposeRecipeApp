package com.example.composerecipeapp.domain.usecases

import com.core.platform.usecase.FlowUseCase
import com.data.repository.repositories.RecipeLocalRepository
import com.example.composerecipeapp.ui.pojo.RecipeModel
import com.example.composerecipeapp.ui.pojo.mapToRecipeEntity

class SaveRecipeUsecase(var localRepository: RecipeLocalRepository) :
    FlowUseCase<Long, SaveRecipeUsecase.Param>() {
    override suspend fun run(params: Param): Long {
        val recipe = mapToRecipeEntity(params.recipeModel)
        return localRepository.insertRecipe(recipe)
    }

    data class Param(var recipeModel: RecipeModel)
}
