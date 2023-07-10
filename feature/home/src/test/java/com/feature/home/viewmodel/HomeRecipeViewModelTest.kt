package com.feature.home.viewmodel

import com.appmattus.kotlinfixture.kotlinFixture
import com.domain.common.pojo.RecipeModel
import com.domain.recipe.cuisines.RecipeWithCuisine
import com.domain.recipe.cuisines.RecipesForSelectedCuisines
import com.feature.home.state.HomeRecipeState
import com.feature.home.state.LoadRecipeEvent
import com.feature.home.state.RefreshHomeEvent
import com.feature.home.state.add
import com.feature.home.state.showLoading
import com.state_manager.extensions.createTestContainer
import com.state_manager.test.StateManagerTestRule
import com.state_manager.test.TestStateManagerScope
import com.state_manager.test.expect
import com.state_manager.test.test
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class HomeRecipeViewModelTest {

    @get:Rule
    var rule = StateManagerTestRule()
    val fixture = kotlinFixture()

    lateinit var viewModel: HomeRecipeViewModel

    @MockK
    lateinit var recipeWithCuisineUseCase: RecipesForSelectedCuisines

    val initialHomeState = HomeRecipeState()
    val recipeWithCuisine by lazy {
        RecipeWithCuisine(
            cuisine = "Indian", recipeList = listOf(
                fixture<RecipeModel>()
            )
        )
    }

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        coEvery { recipeWithCuisineUseCase.invoke() } returns flowOf(recipeWithCuisine)
        viewModel =
            HomeRecipeViewModel(recipeWithCuisineUseCase, initialHomeState, TestStateManagerScope(
                UnconfinedTestDispatcher()
            ), StandardTestDispatcher()
            )
    }

    @Test
    fun `verify load recipe event`() = runTest {
        val state = viewModel.initialState
        val states = listOf(
            state,
            state.showLoading(true),
            state.add(recipeWithCuisine).showLoading(false),
        )
        viewModel.createTestContainer().test {
            forEvents(LoadRecipeEvent)
            verify {
                expect(states)
            }
        }
    }

    @Test
    fun `verify refresh event`() = runTest {
        val state = viewModel.initialState
        val states = listOf(
            state.showLoading(true),
            state.add(recipeWithCuisine).showLoading(false),
        )
        println(states)
        viewModel.createTestContainer().test {
            forEvents(RefreshHomeEvent)
            verify {
                expect(states)
            }
        }
    }
}



