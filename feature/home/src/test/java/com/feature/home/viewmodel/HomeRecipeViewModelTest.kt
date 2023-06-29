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
import com.state_manager.extensions.verifyState
import com.state_manager.test.TestStateManagerScope
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class HomeRecipeViewModelTest {

    val fixture = kotlinFixture()

    lateinit var viewModel: HomeRecipeViewModel

    @MockK
    lateinit var recipeWithCuisineUseCase: RecipesForSelectedCuisines

    val initialHomeState = HomeRecipeState()
    val recipeWithCuisine by lazy {
        RecipeWithCuisine(
            cuisine = "Indian",
            recipeList = listOf(
                fixture<RecipeModel>()
            )
        )
    }

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        coEvery { recipeWithCuisineUseCase.invoke() } returns flowOf(recipeWithCuisine)
        viewModel =
            HomeRecipeViewModel(recipeWithCuisineUseCase, initialHomeState, TestStateManagerScope())
    }

    @Test
    fun `verify load recipe event`() = runTest {
        val state = viewModel.initialState
        val states = listOf(
            state.showLoading(true),
            state.add(recipeWithCuisine)
                .showLoading(false),
        )
        viewModel.verifyState(LoadRecipeEvent) {
            println(states)
            assert(it == states)
        }
    }

    @Test
    fun `verify refresh event`() = runTest {
        val state = viewModel.initialState
        val states = listOf(
            state.showLoading(true),
            state.add(recipeWithCuisine)
                .showLoading(false),
        )
        println(states)
        viewModel.verifyState(RefreshHomeEvent) {
            assertEquals(states, it)
        }
    }
}



