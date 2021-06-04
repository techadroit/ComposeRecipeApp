package com.example.composerecipeapp.domain.usecases

import com.example.composerecipeapp.core.usecase.FlowUseCase
import com.example.composerecipeapp.core.usecase.None
import com.example.composerecipeapp.data.datasource.toRecipeModal
import com.example.composerecipeapp.data.repositories.RecipeLocalRepository
import com.example.composerecipeapp.ui.pojo.RecipeModel

class LoadSavedRecipeUsecase(var localRepository: RecipeLocalRepository) :
    FlowUseCase<List<RecipeModel>, None>() {

    override suspend fun run(params: None): List<RecipeModel> {
        return localRepository.getAllSavedRecipes().map { it.toRecipeModal(true) }
    }
}
