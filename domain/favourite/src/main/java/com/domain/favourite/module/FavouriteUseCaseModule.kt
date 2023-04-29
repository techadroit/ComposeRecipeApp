package module

import com.data.repository.datasource.SettingsDataStore
import com.data.repository.repositories.NewRecipeRepository
import com.data.repository.repositories.RecipeLocalRepository
import com.domain.favourite.DeleteSavedRecipe
import com.domain.favourite.GetSavedRecipeCuisine
import com.domain.favourite.LoadSavedRecipeUsecase
import com.domain.favourite.SaveRecipeUsecase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class FavouriteUseCaseModule {

    @Provides
    fun getSaveRecipeUseCase(localRepository: RecipeLocalRepository) =
        SaveRecipeUsecase(localRepository)

    @Provides
    fun getLoadSavedRecipeUseCase(localRepository: RecipeLocalRepository) =
        LoadSavedRecipeUsecase(localRepository = localRepository)

    @Provides
    fun deleteSavedRecipeUseCase(localRepository: RecipeLocalRepository) =
        DeleteSavedRecipe(localRepository = localRepository)

    @Provides
    fun getSavedRecipeCuisineUseCase(
        recipeRepository: NewRecipeRepository,
        settingsDataStore: SettingsDataStore
    ) =
        GetSavedRecipeCuisine(
            recipeRepository = recipeRepository,
            settingsDataStore = settingsDataStore
        )
}
