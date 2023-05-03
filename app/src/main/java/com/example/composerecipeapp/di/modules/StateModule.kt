package com.example.composerecipeapp.di.modules

import com.example.composerecipeapp.viewmodel.home_recipes.HomeRecipeState
import com.example.composerecipeapp.viewmodel.main.MainViewState
import com.example.composerecipeapp.viewmodel.recipe_detail.RecipeDetailState
import com.example.composerecipeapp.viewmodel.recipe_list.RecipeListState
import com.example.composerecipeapp.viewmodel.recipe_search.SearchState
import com.example.composerecipeapp.viewmodel.save_recipe.SaveRecipeState
import com.example.composerecipeapp.viewmodel.settings.SettingsState
import com.example.composerecipeapp.viewmodel.user_interest.UserInterestState
import com.feature.recipe.video.state.RecipeVideoState
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

    @Provides
    fun getMainViewState() = MainViewState()

    @Provides
    fun getSettingsState() = SettingsState()

    @Provides
    fun getUserInterestState() = UserInterestState()

    @Provides
    fun getHomeRecipeState() = HomeRecipeState()
}
