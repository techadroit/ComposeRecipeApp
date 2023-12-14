package com.feature.recipe.list.viewmodel

import com.domain.recipe.search.AutoCompleteUsecase
import com.feature.recipe.list.state.SearchState
import com.feature.recipe.list.state.SearchTextEvent
import com.state_manager.extension.createTestContainer
import com.state_manager.test.StateManagerTestRule
import com.state_manager.test.expect
import com.state_manager.test.test
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class SearchViewModelTest {
    private val testDispatcher = UnconfinedTestDispatcher()
    @get:Rule
    var rule = com.state_manager.test.StateManagerTestRule()
    private lateinit var viewModel: SearchViewModel
    private val initialState = SearchState()
    @MockK
    lateinit var mockUseCase: AutoCompleteUsecase


    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        viewModel = SearchViewModel(initialState, mockUseCase, testDispatcher)
    }

    @Test
    fun `test search for keyword`() {
        // Arrange
        val keyword = "example"
        val resultList = listOf("example1", "example2")
        coEvery { mockUseCase.invoke(keyword) } returns flowOf(resultList)

        // Act
        viewModel.createTestContainer().test {
            withDispatcher(testDispatcher)
            forEvents(SearchTextEvent(keyword))
            verify {
                expect(
                    initialState,
                    initialState.copy(list = resultList)
                )
            }
        }
    }
}
