package com.example.composerecipeapp.domain.usecases

import com.core.platform.usecase.FlowUseCase
import com.core.platform.usecase.None
import com.example.composerecipeapp.data.repositories.NewRecipeRepository
import com.example.composerecipeapp.data.repositories.RecipeRepository

class GetSupportedCuisineUsecase(val recipeRepository: NewRecipeRepository) : FlowUseCase<List<String>, None>() {
    override suspend fun run(params: None): List<String> = recipeRepository.getSupportedCuisine()
}
