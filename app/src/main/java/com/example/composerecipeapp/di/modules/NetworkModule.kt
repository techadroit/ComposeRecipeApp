package com.example.composerecipeapp.di.modules

import com.example.composerecipeapp.BuildConfig
import com.example.composerecipeapp.core.network.DebugServiceProvider
import com.example.composerecipeapp.core.network.NetworkServiceProvider
import com.example.composerecipeapp.core.network.api_service.NewRecipeApi
import com.example.composerecipeapp.core.network.api_service.RecipeApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    fun getServiceProvider(): NetworkServiceProvider = if (BuildConfig.DEBUG) {
        DebugServiceProvider()
    } else {
        throw IllegalStateException("Initialize Your Prod Services with production url.")
    }

    @Provides
    fun getRecipeApiService(networkServiceProvider: NetworkServiceProvider) =
        networkServiceProvider.getService(RecipeApi::class.java)

    @Singleton
    @Provides
    fun getNewRecipeApiService(networkServiceProvider: NetworkServiceProvider) =
        networkServiceProvider.getService(NewRecipeApi::class.java)
}
