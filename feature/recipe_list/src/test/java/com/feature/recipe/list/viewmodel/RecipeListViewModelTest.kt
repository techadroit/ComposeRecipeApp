package com.feature.recipe.list.viewmodel


import com.appmattus.kotlinfixture.kotlinFixture
import com.domain.common.pojo.RecipeModel
import com.domain.favourite.DeleteSavedRecipe
import com.domain.favourite.SaveRecipeUsecase
import com.domain.recipe.search.SearchRecipeUsecase
import com.feature.recipe.list.state.LoadRecipes
import com.feature.recipe.list.state.RecipeListState
import com.feature.recipe.list.state.RefreshRecipeList
import com.feature.recipe.list.state.RemoveSavedRecipeEvent
import com.feature.recipe.list.state.SaveRecipeEvent
import com.feature.recipe.list.state.onLoading
import com.feature.recipe.list.state.onRecipeLoad
import com.feature.recipe.list.state.onRecipeRemovedFromSavedList
import com.feature.recipe.list.state.onRecipeSaved
import com.state_manager.extensions.createTestContainer
import com.state_manager.test.StateManagerTestRule
import com.state_manager.test.TestStateManagerScope
import com.state_manager.test.expect
import com.state_manager.test.test
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class RecipeListViewmodelTest {

    @get:Rule
    var rule = StateManagerTestRule()

    private lateinit var viewModel: RecipeListViewmodel

    private val initialState = RecipeListState()

    val fixture = kotlinFixture()

    @MockK
    lateinit var savedRecipeUseCase: SaveRecipeUsecase

    @MockK
    lateinit var searchUseCase: SearchRecipeUsecase

    @MockK
    lateinit var deleteSavedRecipe: DeleteSavedRecipe

    private val testStateManagerScope = TestStateManagerScope(rule.dispatcher)
    val recipeList: List<RecipeModel> = listOf(fixture<RecipeModel>())

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        coEvery { searchUseCase(any()) } returns flowOf(recipeList to false)
        viewModel = RecipeListViewmodel(
            initialState,
            savedRecipeUseCase,
            searchUseCase,
            deleteSavedRecipe,
            testStateManagerScope,
            StandardTestDispatcher()
        )
    }

    @Test
    fun `test load recipes`() {
        val query = "italian"
        val initialState = viewModel.initialState

        viewModel.createTestContainer().test {
            forEvents(LoadRecipes(query, false))
            verify {
                expect(
                    initialState,
                    initialState.onLoading(query = query),
                    initialState.onLoading(query = query)
                        .onRecipeLoad(false, false, recipeList)
                )
            }
        }
    }

    @Test
    fun `test paginate recipes`() {
        val query = "italian"

        viewModel.createTestContainer().test {
            forEvents(LoadRecipes(query, true))
            verify {
                expect(
                    initialState,
                    initialState.onLoading(true),
                    initialState.onRecipeLoad(true, false, recipeList)
                )
            }
        }
    }

    @Test
    fun `test save recipe`() {
        val recipeModel: RecipeModel = fixture<RecipeModel>()
        coEvery { savedRecipeUseCase(any()) } returns flowOf(1L)

        viewModel.createTestContainer().test {
            forEvents(SaveRecipeEvent(recipeModel))
            verify {
                expect(initialState.onRecipeSaved(recipeModel.id))
            }
        }
    }

    @Test
    fun `test remove saved recipe`() {
        val recipeModel: RecipeModel = fixture<RecipeModel>()
        coEvery { deleteSavedRecipe(any()) } returns flowOf(1)

        viewModel.createTestContainer().test {
            forEvents(RemoveSavedRecipeEvent(recipeModel))
            verify {
                expect(initialState.onRecipeRemovedFromSavedList(recipeModel.id))
            }
        }
    }

    @Test
    fun `test refresh recipe list`() {
        val initialState = viewModel.initialState

        viewModel.createTestContainer().test {
            forEvents(RefreshRecipeList)
            verify {
                expect(initialState)
            }
        }
    }
}
