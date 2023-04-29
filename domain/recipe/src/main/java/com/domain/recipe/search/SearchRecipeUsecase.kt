package com.domain.recipe.search

import com.core.platform.usecase.NewFlowUseCase
import com.data.repository.repositories.NewRecipeRepository
import com.data.repository.repositories.RecipeLocalRepository
import com.domain.common.pojo.RecipeModel
import com.domain.common.pojo.toRecipeModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SearchRecipeUsecase(
    var recipeRepository: NewRecipeRepository,
    var localRepository: RecipeLocalRepository
) :
    NewFlowUseCase<Pair<List<RecipeModel>, Boolean>, SearchRecipeUsecase.Param>() {
    override fun run(params: Param): Flow<Pair<List<RecipeModel>, Boolean>> =
        recipeRepository.getRecipeForCuisine(
            params.cuisine,
            params.limitLicense, params.number, params.offset
        ).map { response ->

            val savedList = localRepository.getAllSavedRecipes().map { it.id }
            val recipeList = response.toRecipeModel {
                savedList.contains(it)
            }
            val endOfList = params.number * params.offset >= response.totalResults
            Pair(recipeList, endOfList)
        }

    data class Param(
        var limitLicense: Boolean = true,
        var cuisine: String,
        var number: Int = 10,
        var offset: Int = 0
    )
}

