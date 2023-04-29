package com.domain.recipe.di

import com.data.repository.datasource.SettingsDataStore
import com.data.repository.repositories.NewRecipeRepository
import com.data.repository.repositories.RecipeLocalRepository
import com.domain.recipe.GetRecipeDetailUsecase
import com.domain.recipe.SimilarRecipeUsecase
import com.domain.recipe.cuisines.GetSupportedCuisineUsecase
import com.domain.recipe.cuisines.RecipesForSelectedCuisines
import com.domain.recipe.search.AutoCompleteUsecase
import com.domain.recipe.search.SearchRecipeUsecase
import com.domain.recipe.video.SearchVideoRecipeUsecase
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
}
