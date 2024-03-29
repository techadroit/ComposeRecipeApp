    package com.domain.recipe

    import com.core.platform.usecase.NewFlowUseCase
    import com.data.repository.repositories.NewRecipeRepository
    import com.domain.common.pojo.RecipeModel
    import kotlinx.coroutines.flow.Flow
    import kotlinx.coroutines.flow.map

    class SimilarRecipeUsecase(var recipeRepository: NewRecipeRepository) :
        NewFlowUseCase<List<RecipeModel>, SimilarRecipeUsecase.Param>() {
        override fun run(params: Param): Flow<List<RecipeModel>> = recipeRepository.getSimilarRecipeFor(
            params.limitLicense, params.id, params.number
        ).map { response ->
            response.map {
                RecipeModel(
                    it.id,
                    it.title,
                    it.servings,
                    it.sourceUrl,
                    it.readyInMinutes
                )
            }
        }

        data class Param(
            var limitLicense: Boolean = true,
            var id: String,
            var number: Int = 10
        )
    }
