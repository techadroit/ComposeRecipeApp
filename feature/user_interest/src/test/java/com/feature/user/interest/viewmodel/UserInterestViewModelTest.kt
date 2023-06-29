package com.feature.user.interest.viewmodel

import com.appmattus.kotlinfixture.kotlinFixture
import com.core.platform.usecase.None
import com.data.repository.datasource.SettingsDataStore
import com.domain.common.pojo.Cuisine
import com.domain.recipe.cuisines.GetSupportedCuisineUsecase
import com.feature.user.interest.state.LoadSupportedCuisine
import com.feature.user.interest.state.OnCuisineSelected
import com.feature.user.interest.state.RemoveCuisine
import com.feature.user.interest.state.SelectedCuisine
import com.feature.user.interest.state.UserInterestSelected
import com.feature.user.interest.state.UserInterestState
import com.feature.user.interest.state.onCuisineRemoved
import com.feature.user.interest.state.onCuisineSelected
import com.state_manager.extensions.verifySideEffects
import com.state_manager.extensions.verifyState
import com.state_manager.test.TestStateManagerScope
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.TestCoroutineScheduler
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class UserInterestViewModelTest {

    @MockK
    lateinit var useCase: GetSupportedCuisineUsecase
    val coroutineScheduler = TestCoroutineScheduler()

    @MockK
    lateinit var settingsDataStore: SettingsDataStore
    val fixture = kotlinFixture()

    lateinit var viewModel: UserInterestViewModel
    var initialState = UserInterestState()
    val cuisineResponse = fixture<List<String>>()
    val cuisine = cuisineResponse.map { Cuisine(name = it) }

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        coEvery { useCase(params = None) } returns flowOf(cuisineResponse)
        viewModel =
            UserInterestViewModel(useCase, initialState, settingsDataStore, TestStateManagerScope(coroutineScheduler))
    }

    @Test
    fun `test load cuisines`() {
        val states = mutableListOf(
            initialState,
            UserInterestState(cuisine)
        )
        viewModel.verifyState(coroutineScheduler,LoadSupportedCuisine) {
            assertEquals(states, it)
        }
    }

    @Test
    fun `test add cuisine event`() {
        val selectedCuisine = cuisine.first()
        val states = mutableListOf(
            initialState,
            UserInterestState(cuisine),
            UserInterestState(cuisine).onCuisineSelected(selectedCuisine)
        )
        viewModel.verifyState(coroutineScheduler,LoadSupportedCuisine, SelectedCuisine(selectedCuisine)) {
            assertEquals(states, it)
        }
    }

    @Test
    fun `test remove cuisine event`() {
        val selectCuisine = cuisine.first()
        val cuisineToBeRemove = selectCuisine.copy(isSelected = true)
        val states = mutableListOf(
            initialState,
            UserInterestState(cuisine),
            UserInterestState(cuisine).onCuisineSelected(selectCuisine),
            UserInterestState(cuisine).onCuisineRemoved(cuisineToBeRemove)
        )
        viewModel.verifyState(
            coroutineScheduler,
            LoadSupportedCuisine,
            SelectedCuisine(selectCuisine),
            RemoveCuisine(cuisineToBeRemove)
        ) {
            println(it)
            assertEquals(states, it)
        }
    }

    @Test
    fun `test side Effect on user interest selected`(){
        val c = cuisine.map { it.copy(isSelected = true) }
        val initialState = UserInterestState(cuisines = c)
        val viewModel = UserInterestViewModel(useCase, initialState, settingsDataStore, TestStateManagerScope(coroutineScheduler))

        viewModel.verifySideEffects(
            coroutineScheduler,
            UserInterestSelected,
        ) {
            assertEquals(listOf(OnCuisineSelected),it)
        }
    }
}