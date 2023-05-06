package com.example.composerecipeapp.di.modules

import com.feature.home.state.HomeRecipeState
import com.example.composerecipeapp.viewmodel.main.MainViewState
import com.example.composerecipeapp.viewmodel.settings.SettingsState
import com.feature.recipe.detail.state.RecipeDetailState
import com.feature.recipe.list.state.RecipeListState
import com.feature.recipe.video.state.RecipeVideoState
import com.feature.saved.recipes.state.SaveRecipeState
import com.feature.user.interest.state.UserInterestState
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
    fun getRecipeSearchState() = com.feature.recipe.list.state.SearchState()

    @Provides
    fun getRecipeDetailState() = RecipeDetailState()

    @Provides
    fun getSaveRecipeState() = SaveRecipeState()

    @Provides
    fun getMainViewState() = MainViewState()

    @Provides
    fun getSettingsState() = SettingsState()

    @Provides
    fun getUserInterestState() = UserInterestState()

    @Provides
    fun getHomeRecipeState() = HomeRecipeState()
}
