package com.example.composerecipeapp.domain.usecases

import com.example.composerecipeapp.core.usecase.FlowUseCase
import com.example.composerecipeapp.core.usecase.None
import com.example.composerecipeapp.data.repositories.RecipeRepository

class GetSupportedCuisineUsecase(val recipeRepository: RecipeRepository) : FlowUseCase<List<String>, None>() {
    override suspend fun run(params: None): List<String> = recipeRepository.getSupportedCuisine()
}
