package com.example.composerecipeapp.domain.usecases

import com.example.composerecipeapp.core.usecase.NewFlowUseCase
import com.example.composerecipeapp.core.usecase.None
import com.example.composerecipeapp.data.datasource.SettingsDataStore
import com.example.composerecipeapp.data.repositories.RecipeRepository
import com.example.composerecipeapp.ui.pojo.RecipeModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RecipesForSelectedCuisines(
    val recipeRepository: RecipeRepository,
    val settingsDataStore: SettingsDataStore
) : NewFlowUseCase<List<RecipeWithCuisine>, None>() {
    override fun run(params: None): Flow<List<RecipeWithCuisine>> =
        settingsDataStore.getCuisines().map { it ->
            val result = mutableListOf<RecipeWithCuisine>()
            it.forEach { cuisine ->
                val response = recipeRepository.getRecipeForCuisine(cuisine, true, 10, 1)
                val recipeList = response.results.map {
                    RecipeModel(
                        it.id,
                        it.title,
                        it.servings,
                        response.baseUri + it.image,
                        it.readyInMinutes,
                        isSaved = false
                    )
                }
                result.add(RecipeWithCuisine(cuisine, recipeList))
            }
            result
        }
}

data class RecipeWithCuisine(val cuisine: String, val recipeList: List<RecipeModel>)
