package com.data.repository.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Singleton
    @Provides
    fun getNewRemoteRepository(apiService: com.data.repository.NewRecipeApi) =
        com.data.repository.repositories.NewRecipeRepository(apiService)

    @Provides
    fun getLocalRepository(recipeDao: com.data.repository.datasource.RecipeDao) =
        com.data.repository.repositories.RecipeLocalRepository(recipeDao = recipeDao)
}
