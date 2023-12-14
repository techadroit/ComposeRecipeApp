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
import com.state_manager.extension.createTestContainer
import com.state_manager.test.StateManagerTestRule
import com.state_manager.test.TestStateManagerScope
import com.state_manager.test.expect
import com.state_manager.test.test
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.flow.flowOf
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class UserInterestViewModelTest {

    @get:Rule
    var rule = com.state_manager.test.StateManagerTestRule()

    @MockK
    lateinit var useCase: GetSupportedCuisineUsecase

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
            UserInterestViewModel(useCase, initialState, settingsDataStore,
                com.state_manager.test.TestStateManagerScope()
            )
    }

    @Test
    fun `test load cuisines`() {
        val states = mutableListOf(
            initialState,
            UserInterestState(cuisine)
        )
        viewModel.createTestContainer().test {
            forEvents(LoadSupportedCuisine)
            verify {
                expect(states)
            }
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
        viewModel.createTestContainer().test {
            forEvents(LoadSupportedCuisine,SelectedCuisine(selectedCuisine))
            verify {
                expect(states)
            }
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
        viewModel.createTestContainer().test {
            forEvents(LoadSupportedCuisine,
                SelectedCuisine(selectCuisine),
                RemoveCuisine(cuisineToBeRemove))
            verify { expect(states) }
        }
    }

    @Test
    fun `test on user interest selected`(){
        val c = cuisine.map { it.copy(isSelected = true) }
        val initialState = UserInterestState(cuisines = c)
        val viewModel =
                UserInterestViewModel(useCase, initialState, settingsDataStore,
                    com.state_manager.test.TestStateManagerScope()
                )
        viewModel.createTestContainer().test {
            forEvents(UserInterestSelected)
            verifyEffects {
                expect(OnCuisineSelected)
            }
        }
    }
}