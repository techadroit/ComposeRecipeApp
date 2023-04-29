package com.domain.recipe.video

import com.core.platform.usecase.NewFlowUseCase
import com.data.repository.repositories.NewRecipeRepository
import com.domain.common.pojo.VideoRecipeModel
import com.domain.common.pojo.toRecipeModel
import com.domain.recipe.util.NUMBER
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SearchVideoRecipeUsecase(var recipeRepository: NewRecipeRepository) :
    NewFlowUseCase<List<VideoRecipeModel>, SearchVideoRecipeUsecase.Param>() {
    override fun run(params: Param): Flow<List<VideoRecipeModel>> {
        return recipeRepository.searchVideoRecipeFor(params.query, params.number, params.offset)
            .map {
                it.toRecipeModel()
            }
    }

    data class Param(var query: String, var number: Int = NUMBER, var offset: Int = 0)
}
