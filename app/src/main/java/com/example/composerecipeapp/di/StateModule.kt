package com.example.composerecipeapp.di

import com.example.composerecipeapp.ui.recipes.SearchState
import com.example.composerecipeapp.ui.viewmodel.RecipeDetailState
import com.example.composerecipeapp.ui.viewmodel.RecipeListState
import com.recipeapp.view.viewmodel.RecipeVideoState
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class StateModule {

    @Provides
    fun getRecipeViewState() = RecipeListState()

    @Provides
    fun getRecipeVideoState() = RecipeVideoState()

    @Provides
    fun getRecipeSearchState() = SearchState()

    @Provides
    fun getRecipeDetailState() = RecipeDetailState()
}
