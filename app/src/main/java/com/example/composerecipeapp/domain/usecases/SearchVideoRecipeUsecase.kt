package com.example.composerecipeapp.domain.usecases

import com.core.platform.usecase.NewFlowUseCase
import com.example.composerecipeapp.data.repositories.NewRecipeRepository
import com.example.composerecipeapp.ui.pojo.VideoRecipeModel
import com.example.composerecipeapp.util.NUMBER
import com.example.composerecipeapp.data.network.response.toRecipeModel
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
