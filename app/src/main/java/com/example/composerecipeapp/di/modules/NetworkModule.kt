package com.example.composerecipeapp.di.modules

import com.example.composerecipeapp.core.network.NetworkHandler
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    fun provideNetworkHandler() = NetworkHandler

    @Provides
    fun getRecipeApiService() = NetworkHandler.getRecipeService()
}
