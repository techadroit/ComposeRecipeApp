package com.example.composerecipeapp.di.modules

import com.example.composerecipeapp.core.network.api_service.NewRecipeApi
import com.example.composerecipeapp.core.network.api_service.RecipeApi
import com.example.composerecipeapp.data.datasource.RecipeDao
import com.example.composerecipeapp.data.repositories.NewRecipeRepository
import com.example.composerecipeapp.data.repositories.RecipeLocalRepository
import com.example.composerecipeapp.data.repositories.RecipeRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    fun getRemoteRepository(apiService: RecipeApi) = RecipeRepository(apiService)

    @Singleton
    @Provides
    fun getNewRemoteRepository(apiService: NewRecipeApi) = NewRecipeRepository(apiService)

    @Provides
    fun getLocalRepository(recipeDao: RecipeDao) = RecipeLocalRepository(recipeDao = recipeDao)
}
