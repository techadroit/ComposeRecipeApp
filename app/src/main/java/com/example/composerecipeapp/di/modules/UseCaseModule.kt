package com.example.composerecipeapp.di.modules

import com.example.composerecipeapp.data.repositories.RecipeRepository
import com.example.composerecipeapp.domain.usecases.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class UseCaseModule {

    @Provides
    fun getSimilarRecipeUseCase(repository: RecipeRepository) = SimilarRecipeUsecase(repository)

    @Provides
    fun getSearchRecipeUseCase(repository: RecipeRepository) = SearchRecipeUsecase(repository)

    @Provides
    fun getSearchVideoRecipeUseCase(repository: RecipeRepository) = SearchVideoRecipeUsecase(repository)

    @Provides
    fun getRecipeDetailViewUseCase(repository: RecipeRepository) = GetRecipeDetailUsecase(repository)

    @Provides
    fun getAutoCompleteUseCase(repository: RecipeRepository) = AutoCompleteUsecase(repository)

//    @Provides
//    fun getSavedRecipeUseCase(repository: RecipeLocalRepository) = SaveRecipeUsecase(repository)
}
