package com.example.composerecipeapp.domain.usecases

import com.example.composerecipeapp.core.usecase.NewFlowUseCase
import com.example.composerecipeapp.core.usecase.None
import com.example.composerecipeapp.data.datasource.SettingsDataStore
import com.example.composerecipeapp.data.repositories.RecipeRepository
import com.example.composerecipeapp.viewmodel.user_interest.Cuisine
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetSavedRecipeCuisine(
    val recipeRepository: RecipeRepository,
    val settingsDataStore: SettingsDataStore
) : NewFlowUseCase<List<Cuisine>, None>() {
    override fun run(params: None): Flow<List<Cuisine>> =
        settingsDataStore.getCuisines().map { list ->
            val supportedCuisine = recipeRepository.getSupportedCuisine()
                .map { Cuisine(it, isSelected = list.contains(it)) }
            supportedCuisine
        }
}
