package com.data.repository.di

import com.core.network.service_provider.NetworkServiceProvider
import com.data.repository.NewRecipeApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApiModule {

    @Singleton
    @Provides
    fun getNewRecipeApiService(networkServiceProvider: NetworkServiceProvider) =
        networkServiceProvider.getService(NewRecipeApi::class.java)
}
