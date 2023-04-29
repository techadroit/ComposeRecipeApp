package com.domain.recipe

import com.core.platform.usecase.NewFlowUseCase
import com.data.repository.repositories.NewRecipeRepository
import com.data.repository.repositories.RecipeLocalRepository
import com.domain.common.pojo.RecipeDetailModel
import com.domain.common.pojo.toRecipeDetailModel
import kotlinx.coroutines.flow.Flow
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
