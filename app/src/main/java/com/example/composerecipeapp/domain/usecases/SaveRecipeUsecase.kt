package com.example.composerecipeapp.domain.usecases

import com.example.composerecipeapp.core.usecase.FlowUseCase
import com.example.composerecipeapp.data.repositories.RecipeLocalRepository
import com.example.composerecipeapp.data.repositories.mapToRecipeEntity
import com.example.composerecipeapp.ui.pojo.RecipeModel


class SaveRecipeUsecase(var localRepository: RecipeLocalRepository) :
    FlowUseCase<Long, SaveRecipeUsecase.Param>() {
    override suspend fun run(params: Param): Long {
        val recipe = mapToRecipeEntity(params.recipeModel)
        return localRepository.insertRecipe(recipe)
    }

    data class Param(var recipeModel: RecipeModel)
}

