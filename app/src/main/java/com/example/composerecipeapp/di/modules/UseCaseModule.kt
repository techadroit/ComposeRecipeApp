package com.example.composerecipeapp.di.modules

import com.example.composerecipeapp.data.datasource.SettingsDataStore
import com.example.composerecipeapp.data.repositories.RecipeLocalRepository
import com.example.composerecipeapp.data.repositories.RecipeRepository
import com.example.composerecipeapp.domain.usecases.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class UseCaseModule {

    @Provides
    fun getSimilarRecipeUseCase(repository: RecipeRepository) = SimilarRecipeUsecase(repository)

    @Provides
    fun getSearchRecipeUseCase(
        repository: RecipeRepository,
        localRepository: RecipeLocalRepository
    ) = SearchRecipeUsecase(repository, localRepository)

    @Provides
    fun getSearchVideoRecipeUseCase(repository: RecipeRepository) =
        SearchVideoRecipeUsecase(repository)

    @Provides
    fun getRecipeDetailViewUseCase(
        repository: RecipeRepository,
        localRepository: RecipeLocalRepository
    ) =
        GetRecipeDetailUsecase(repository, localRepository)

    @Provides
    fun getAutoCompleteUseCase(repository: RecipeRepository) = AutoCompleteUsecase(repository)

    @Provides
    fun getSaveRecipeUseCase(localRepository: RecipeLocalRepository) =
        SaveRecipeUsecase(localRepository)

    @Provides
    fun getLoadSavedRecipeUseCase(localRepository: RecipeLocalRepository) =
        LoadSavedRecipeUsecase(localRepository = localRepository)

    @Provides
    fun deleteSavedRecipeUseCase(localRepository: RecipeLocalRepository) =
        DeleteSavedRecipe(localRepository = localRepository)

    @Provides
    fun getCuisineUsecase(recipeRepository: RecipeRepository) =
        GetSupportedCuisineUsecase(recipeRepository = recipeRepository)

    @Provides
    fun getRecipeWithCuisineUseCase(
        recipeRepository: RecipeRepository,
        settingsDataStore: SettingsDataStore
    ) =
        RecipesForSelectedCuisines(
            recipeRepository = recipeRepository,
            settingsDataStore = settingsDataStore
        )

    @Provides
    fun getSavedRecipeCuisineUseCase(
        recipeRepository: RecipeRepository,
        settingsDataStore: SettingsDataStore
    ) =
        GetSavedRecipeCuisine(
            recipeRepository = recipeRepository,
            settingsDataStore = settingsDataStore
        )
}
