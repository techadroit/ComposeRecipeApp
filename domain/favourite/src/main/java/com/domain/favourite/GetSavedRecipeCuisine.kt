package com.domain.favourite

import com.core.platform.usecase.NewFlowUseCase
import com.core.platform.usecase.None
import com.data.repository.datasource.SettingsDataStore
import com.data.repository.repositories.NewRecipeRepository
import com.domain.common.pojo.Cuisine
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetSavedRecipeCuisine(
    val recipeRepository: NewRecipeRepository,
    val settingsDataStore: SettingsDataStore
) : NewFlowUseCase<List<Cuisine>, None>() {
    override fun run(params: None): Flow<List<Cuisine>> =
        settingsDataStore.getCuisines().map { list ->
            val supportedCuisine = recipeRepository.getSupportedCuisine()
                .map { Cuisine(it, isSelected = list.contains(it)) }
            supportedCuisine
        }
}
