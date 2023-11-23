package com.feature.settings.viewmodel

import com.data.repository.datasource.SettingsDataStore
import com.domain.common.pojo.Cuisine
import com.domain.favourite.GetSavedRecipeCuisine
import com.feature.settings.state.SettingsState
import com.feature.settings.state.deselectCuisine
import com.feature.settings.state.getSelectedCuisine
import com.feature.settings.state.onCuisineSaved
import com.state_manager.extensions.createTestContainer
import com.state_manager.test.StateManagerTestRule
import com.state_manager.test.TestStateManagerScope
import com.state_manager.test.expect
import com.state_manager.test.has
import com.state_manager.test.test
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class SettingsViewModelTest {

    @get:Rule
    val rule = StateManagerTestRule()

    private lateinit var viewModel: SettingsViewModel
    private val initialState = SettingsState()
    private val mockSettingsDataStore: SettingsDataStore = mockk(relaxed = true)
    private val mockGetSavedRecipeCuisine: GetSavedRecipeCuisine = mockk(relaxed = true)
    private val testStateManagerScope = TestStateManagerScope()

    // Arrange
    val cuisine1 = Cuisine("cuisine1", isSelected = false)
    val cuisine2 = Cuisine("cuisine2", isSelected = false)
    val cuisine3 = Cuisine("cuisine3", isSelected = false)
    val cuisine4 = Cuisine("cuisine4", isSelected = false)
    val cuisine5 = Cuisine(" cuisine5", isSelected = false)
    val cuisineList = listOf(cuisine1, cuisine2, cuisine3, cuisine4, cuisine5)

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        viewModel = SettingsViewModel(
            initialState,
            mockSettingsDataStore,
            mockGetSavedRecipeCuisine,
            testStateManagerScope
        )
    }

    @Test
    fun `test save cuisine`() {

        val updatedState = initialState.copy(list = cuisineList)

        coEvery { mockSettingsDataStore.storeCuisine(any()) } returns Unit

        // Act
        viewModel.createTestContainer().test {
            withState(updatedState)
            forActions(
                { viewModel.saveCuisine() }
            )
            verify {
                expect(
                    updatedState,
                    updatedState.onCuisineSaved()
                )
            }
        }
    }

    @Test
    fun `test select cuisine`() {
        val selectedCuisine = cuisine2
        val updatedState = initialState.copy(list = cuisineList)

        // Act
        viewModel.createTestContainer().test {
            withState(updatedState)
            forActions(
                { viewModel.cuisineSelected(selectedCuisine) }
            )
            verify {
                has {
                    it.getSelectedCuisine().isNotEmpty()
                            && it.getSelectedCuisine()
                        .first() == selectedCuisine.copy(isSelected = true)
                }
            }
        }
    }

    @Test
    fun `test enable next options cuisine`() {
        val selectedCuisine = cuisine2
        val updatedState = initialState.copy(list = cuisineList)

        // Act
        viewModel.createTestContainer().test {
            withState(updatedState)
            forActions(
                { viewModel.cuisineSelected(cuisine1) },
                { viewModel.cuisineSelected(cuisine2) },
                { viewModel.cuisineSelected(cuisine3) },
                { viewModel.cuisineSelected(cuisine4) },
                { viewModel.cuisineSelected(cuisine5) },
            )
            verify {
                has {
                    it.enableSaveOptions
                }
            }
        }
    }

    @Test
    fun `test emitted state when cuisine is unselected`() {

        /// Arrange
        val deselectedCuisine = cuisine2
        // make all the list as selected
        val updatedState = initialState.copy(list = cuisineList.map { it.copy(isSelected = true) })

        /// Act
        viewModel.createTestContainer().test {
            withState(updatedState)
            forActions(
                { viewModel.cuisineDeSelected(deselectedCuisine) }
            )
            verify {
                expect(
                    updatedState,
                    updatedState
                        .deselectCuisine(deselectedCuisine)
                )
            }
        }
    }

    @Test
    fun `test save options is not enabled when less than 5 cuisine is selected`() {

        /// Arrange
        val deselectedCuisine = cuisine2.copy(isSelected = true)
        // make all the list as selected
        val updatedState = initialState.copy(
            list = cuisineList.map { it.copy(isSelected = true) },
            enableSaveOptions = true
        )

        /// Act
        viewModel.createTestContainer().test {
            withState(updatedState)
            forActions(
                { viewModel.cuisineDeSelected(deselectedCuisine) }
            )
            verify {
                has {
                    !it.enableSaveOptions
                }
            }
        }
    }

//    @Test
//    fun `test change data store setting`() {
//        // Arrange
//        val state = SettingsState(isDarkModeOn = true)
//
//        // Act
//        viewModel.createTestContainer().test {
//            withState(state)
//            forActions({
//                viewModel.changeDataStoreSetting(false)
//            })
//            // No state change is expected for this action
//            verify {
//                has {
//                    !it.isDarkModeOn
//                }
//            }
//        }
//    }

//    @Test
//    fun `test initialize`() {
//        // Arrange
//        val isDarkModeOn = true
//        val cuisineList = listOf(Cuisine("cuisine1"), Cuisine("cuisine2"))
//        coEvery { mockSettingsDataStore.isDarkModeOn() } returns flowOf(isDarkModeOn)
//        coEvery { mockGetSavedRecipeCuisine(None) } returns flowOf(cuisineList)
//
//        // Act
//        viewModel.createTestContainer().test {
//            viewModel.initialize()
//            verify {
//                expect(
//                    initialState,
//                    initialState.initialize(isDarkModeOn, cuisineList)
//                )
//            }
//        }
//    }
}
