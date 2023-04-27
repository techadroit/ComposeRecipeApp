package com.example.composerecipeapp.domain.usecases

import com.core.platform.usecase.FlowUseCase
import com.core.platform.usecase.None
import com.data.repository.repositories.RecipeLocalRepository
import com.example.composerecipeapp.ui.pojo.RecipeModel
import com.example.composerecipeapp.ui.pojo.toRecipeModal

class LoadSavedRecipeUsecase(var localRepository: RecipeLocalRepository) :
    FlowUseCase<List<RecipeModel>, None>() {

    override suspend fun run(params: None): List<RecipeModel> {
        return localRepository.getAllSavedRecipes().map {
            it.toRecipeModal(true)
        }
    }
}
