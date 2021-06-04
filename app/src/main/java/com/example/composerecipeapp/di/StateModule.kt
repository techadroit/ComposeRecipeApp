package com.example.composerecipeapp.di

import com.example.composerecipeapp.viewmodel.recipe_detail.RecipeDetailState
import com.example.composerecipeapp.viewmodel.recipe_list.RecipeListState
import com.example.composerecipeapp.viewmodel.recipe_search.SearchState
import com.example.composerecipeapp.viewmodel.recipe_video.RecipeVideoState
import com.example.composerecipeapp.viewmodel.save_recipe.SaveRecipeState
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

    @Provides
    fun getSaveRecipeState() = SaveRecipeState()
}
