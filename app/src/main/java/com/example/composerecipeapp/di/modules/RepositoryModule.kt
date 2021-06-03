package com.example.composerecipeapp.di.modules

import com.example.composerecipeapp.data.repositories.RecipeRepository
import com.recipeapp.core.network.api_service.RecipeApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    fun getRemoteRepository(apiService: RecipeApi) = RecipeRepository(apiService)
}
