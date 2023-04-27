package com.example.composerecipeapp.domain.usecases

import com.core.platform.usecase.FlowUseCase
import com.data.repository.repositories.RecipeLocalRepository

class DeleteSavedRecipe(val localRepository: RecipeLocalRepository) : FlowUseCase<Int, Int>() {
    override suspend fun run(params: Int): Int {
        return localRepository.deleteRecipes(params)
    }
}
