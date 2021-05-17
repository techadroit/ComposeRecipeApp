package com.example.composerecipeapp.domain.usecases

import com.example.composerecipeapp.core.exception.Failure
import com.example.composerecipeapp.core.functional.Either
import com.example.composerecipeapp.core.usecase.UseCase
import com.example.composerecipeapp.data.repositories.RecipeLocalRepository
import com.example.composerecipeapp.data.repositories.mapToRecipeEntity
import com.example.composerecipeapp.ui.pojo.RecipeModel

class SaveRecipeUsecase(var localRepository: RecipeLocalRepository) :
    UseCase<Long, SaveRecipeUsecase.Param>() {
    override suspend fun run(params: Param): Either<Failure, Long> {
        val recipe = mapToRecipeEntity(params.recipeModel)
        return localRepository.insertRecipe(recipe)
    }

    data class Param(var recipeModel: RecipeModel)
}

