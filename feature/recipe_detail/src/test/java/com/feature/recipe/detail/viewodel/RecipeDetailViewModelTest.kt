package com.feature.recipe.detail.viewodel

import com.appmattus.kotlinfixture.kotlinFixture
import com.domain.common.pojo.RecipeDetailModel
import com.domain.common.pojo.RecipeModel
import com.domain.favourite.DeleteSavedRecipe
import com.domain.favourite.SaveRecipeUsecase
import com.domain.recipe.GetRecipeDetailUsecase
import com.domain.recipe.SimilarRecipeUsecase
import com.feature.recipe.detail.state.LoadRecipeDetail
import com.feature.recipe.detail.state.RecipeDetailState
import com.feature.recipe.detail.state.RemoveRecipe
import com.feature.recipe.detail.state.onLoading
import com.feature.recipe.detail.state.onRemoveFromSavedList
import com.feature.recipe.detail.state.onSuccessResponse
import com.feature.recipe.detail.viewmodel.RecipeDetailViewModel
import com.state_manager.extensions.createTestContainer
import com.state_manager.test.StateManagerTestRule
import com.state_manager.test.TestStateManagerScope
import com.state_manager.test.expect
import com.state_manager.test.test
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class RecipeDetailViewModelTest {

    @get:Rule
    val rule = StateManagerTestRule()

    private lateinit var viewModel: RecipeDetailViewModel
    private val initialState = RecipeDetailState()
    private val mockUseCase: GetRecipeDetailUsecase = mockk(relaxed = true)
    private val mockSimilarUseCase: SimilarRecipeUsecase = mockk(relaxed = true)
    private val mockDeleteSavedRecipe: DeleteSavedRecipe = mockk(relaxed = true)
    private val mockSaveRecipeUseCase: SaveRecipeUsecase = mockk(relaxed = true)
    private val fixture = kotlinFixture()
    val recipeModel = fixture<RecipeModel>()
    val recipeDetailModel = fixture<RecipeDetailModel>()
    val similarRecipes = listOf(recipeModel)
    val testStateManagerScope = TestStateManagerScope()

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        viewModel = RecipeDetailViewModel(
            initialState,
            mockUseCase,
            mockSimilarUseCase,
            mockDeleteSavedRecipe,
            mockSaveRecipeUseCase,
            testStateManagerScope,
            testStateManagerScope.testDispatcher
        )

        coEvery { mockUseCase(any()) } returns flowOf(recipeDetailModel)
        coEvery { mockSimilarUseCase(any()) } returns flowOf(similarRecipes)
    }

    @Test
    fun `test load recipe detail`() {

        // Act
        viewModel.createTestContainer().test {
            forEvents(LoadRecipeDetail(recipeModel.id.toString()))
            verify {
                expect(
                    initialState,
                    initialState.onLoading(),
                    initialState.onSuccessResponse(
                        recipeDetail = recipeDetailModel,
                        recipeList = similarRecipes
                    )
                )
            }
        }
    }

    @Test
    fun `test remove recipe`() {
        // Arrange
        val initialState = initialState.copy(recipeDetail = recipeDetailModel)

        coEvery { mockDeleteSavedRecipe(any()) } returns flowOf(1)

        // Act
        viewModel.createTestContainer().test {
            withState(initialState)
            forEvents(RemoveRecipe)
            verify {
                expect(
                    initialState,
                    initialState.onRemoveFromSavedList()
                )
            }
        }
    }
//
//    @Test
//    fun `test save recipe`() {
//        // Arrange
//        val recipeId = "123"
//        val recipeModel = RecipeModel(id = recipeId)
//
//        // Act
//        viewModel.createTestContainer().test {
//            forEvents(SaveRecipe)
//            verify {
//                expect(
//                    initialState,
//                    initialState.copy(recipeDetail = initialState.recipeDetail?.copy(isSaved = true))
//                )
//            }
//        }
//    }
}
