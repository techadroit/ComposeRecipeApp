package com.example.composerecipeapp.di.modules

import com.data.repository.datasource.SettingsDataStore
import com.data.repository.repositories.NewRecipeRepository
import com.data.repository.repositories.RecipeLocalRepository
import com.example.composerecipeapp.domain.usecases.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class UseCaseModule {

    @Provides
    fun getSimilarRecipeUseCase(repository: NewRecipeRepository) =
        SimilarRecipeUsecase(repository)

    @Provides
    fun getSearchRecipeUseCase(
        repository: NewRecipeRepository,
        localRepository: RecipeLocalRepository
    ) = SearchRecipeUsecase(repository, localRepository)

    @Provides
    fun getSearchVideoRecipeUseCase(repository: NewRecipeRepository) =
        SearchVideoRecipeUsecase(repository)

    @Provides
    fun getRecipeDetailViewUseCase(
        repository: NewRecipeRepository,
        localRepository: RecipeLocalRepository
    ) =
        GetRecipeDetailUsecase(repository, localRepository)

    @Provides
    fun getAutoCompleteUseCase(repository: NewRecipeRepository) =
        AutoCompleteUsecase(repository)

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
    fun getCuisineUsecase(recipeRepository: NewRecipeRepository) =
        GetSupportedCuisineUsecase(recipeRepository = recipeRepository)

    @Provides
    fun getRecipeWithCuisineUseCase(
        recipeRepository: NewRecipeRepository,
        settingsDataStore: SettingsDataStore
    ) =
        RecipesForSelectedCuisines(
            recipeRepository = recipeRepository,
            settingsDataStore = settingsDataStore
        )

    @Provides
    fun getSavedRecipeCuisineUseCase(
        recipeRepository: NewRecipeRepository,
        settingsDataStore: SettingsDataStore
    ) =
        GetSavedRecipeCuisine(
            recipeRepository = recipeRepository,
            settingsDataStore = settingsDataStore
        )
}
