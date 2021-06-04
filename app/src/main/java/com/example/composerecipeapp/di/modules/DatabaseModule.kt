package com.example.composerecipeapp.di.modules

import android.content.Context
import com.example.composerecipeapp.data.datasource.RecipeDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    fun provideDatabase(@ApplicationContext context: Context) = RecipeDatabase.getDatabase(context)

    @Provides
    fun provideRecipeDatasource(database: RecipeDatabase) = database.recipeDao()
}
