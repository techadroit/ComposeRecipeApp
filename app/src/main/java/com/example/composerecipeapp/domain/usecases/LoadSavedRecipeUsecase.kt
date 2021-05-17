package com.example.composerecipeapp.domain.usecases

import com.example.composerecipeapp.core.exception.Failure
import com.example.composerecipeapp.core.functional.Either
import com.example.composerecipeapp.core.functional.flatMap
import com.example.composerecipeapp.core.usecase.UseCase
import com.example.composerecipeapp.data.datasource.toRecipeModal
import com.example.composerecipeapp.data.repositories.RecipeLocalRepository
import com.example.composerecipeapp.ui.pojo.RecipeModel

class LoadSavedRecipeUsecase(var localRepository: RecipeLocalRepository) :
    UseCase<List<RecipeModel>, UseCase.None>() {

    override suspend fun run(params: None): Either<Failure, List<RecipeModel>> {
        val response = localRepository.getAllSavedRecipes()
        val mappedReponse = response.flatMap { list ->
            Either.Right(list.map { it.toRecipeModal() }
            )
        }
        return mappedReponse
    }
}