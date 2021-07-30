package com.example.composerecipeapp.di.modules

import android.content.Context
import com.example.composerecipeapp.data.datasource.SettingsDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class DataStoreModule {

    @Provides
    fun provideSettingsDataStore(@ApplicationContext context: Context) = SettingsDataStore(context)
}
