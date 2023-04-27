package com.example.composerecipeapp.domain.usecases

import com.data.repository.datasource.SettingsDataStore
import com.data.repository.repositories.NewRecipeRepository
import com.data.repository.response.RecipeSearchResponse
import com.example.composerecipeapp.ui.pojo.RecipeModel
import com.example.composerecipeapp.ui.pojo.toRecipeModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.map

class RecipesForSelectedCuisines(
    val recipeRepository: NewRecipeRepository,
    val settingsDataStore: SettingsDataStore
) {

    operator fun invoke(): Flow<RecipeWithCuisine> {
        val cuisineFlow: Flow<List<String>> = settingsDataStore.getCuisines()

        return cuisineFlow
            .flatMapConcat { cuisineList ->
                cuisineList.asFlow() // Convert the list to a flow
            }
            .flatMapConcat { cuisine ->
                recipeRepository.getRecipeForCuisine(cuisine, true, 10, 1).map {
                    transform(cuisine, it)
                }
            }
    }

    private fun transform(
        cuisine: String,
        response: RecipeSearchResponse
    ): RecipeWithCuisine {
        val recipeList = response.toRecipeModel { false }
        return RecipeWithCuisine(cuisine, recipeList)
    }
}

data class RecipeWithCuisine(val cuisine: String, val recipeList: List<RecipeModel>)
