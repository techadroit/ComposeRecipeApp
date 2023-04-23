package com.example.composerecipeapp.domain.usecases

import com.example.composerecipeapp.core.usecase.NewFlowUseCase
import com.example.composerecipeapp.data.repositories.NewRecipeRepository
import com.example.composerecipeapp.data.repositories.RecipeLocalRepository
import com.example.composerecipeapp.ui.pojo.RecipeModel
import com.recipeapp.data.network.response.toRecipeModel
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

