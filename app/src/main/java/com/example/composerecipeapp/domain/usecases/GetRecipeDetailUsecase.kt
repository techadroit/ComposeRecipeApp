package com.example.composerecipeapp.domain.usecases

import com.example.composerecipeapp.core.usecase.NewFlowUseCase
import com.example.composerecipeapp.data.network.response.toRecipeDetailModel
import com.example.composerecipeapp.data.repositories.NewRecipeRepository
import com.example.composerecipeapp.data.repositories.RecipeLocalRepository
import com.example.composerecipeapp.ui.pojo.RecipeDetailModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.map

class GetRecipeDetailUsecase(
    var recipeRepository: NewRecipeRepository,
    val localRepository: RecipeLocalRepository
) :
    NewFlowUseCase<RecipeDetailModel, GetRecipeDetailUsecase.Param>() {
    override fun run(params: Param): Flow<RecipeDetailModel> =
        recipeRepository.getRecipeDetailForId(params.id, params.includeNutrition).map {
            val isSaved = localRepository.isSaved(it.id)
            it.toRecipeDetailModel(isSaved = isSaved)
        }

    data class Param(var includeNutrition: Boolean = true, var id: String)
}
