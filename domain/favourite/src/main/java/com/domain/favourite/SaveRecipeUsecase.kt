package com.domain.favourite

import com.core.platform.usecase.FlowUseCase
import com.data.repository.repositories.RecipeLocalRepository
import com.domain.common.pojo.RecipeModel
import com.domain.common.pojo.mapToRecipeEntity

class SaveRecipeUsecase(var localRepository: RecipeLocalRepository) :
    FlowUseCase<Long, SaveRecipeUsecase.Param>() {
    override suspend fun run(params: Param): Long {
        val recipe = mapToRecipeEntity(params.recipeModel)
        return localRepository.insertRecipe(recipe)
    }

    data class Param(var recipeModel: RecipeModel)
}
