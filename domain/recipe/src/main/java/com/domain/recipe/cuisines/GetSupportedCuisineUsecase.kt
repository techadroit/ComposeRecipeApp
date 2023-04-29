package com.domain.recipe.cuisines

import com.core.platform.usecase.FlowUseCase
import com.core.platform.usecase.None
import com.data.repository.repositories.NewRecipeRepository

class GetSupportedCuisineUsecase(val recipeRepository: NewRecipeRepository) : FlowUseCase<List<String>, None>() {
    override suspend fun run(params: None): List<String> = recipeRepository.getSupportedCuisine()
}
