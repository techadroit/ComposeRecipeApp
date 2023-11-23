package com.feature.recipe.list.viewmodel

import com.domain.recipe.search.AutoCompleteUsecase
import com.feature.recipe.list.state.SearchState
import com.feature.recipe.list.state.SearchTextEvent
import com.state_manager.extensions.createTestContainer
import com.state_manager.test.StateManagerTestRule
import com.state_manager.test.TestStateManagerScope
import com.state_manager.test.expect
import com.state_manager.test.test
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class SearchViewModelTest {
    private lateinit var viewModel: SearchViewModel
    private val initialState = SearchState()
    private val mockUseCase: AutoCompleteUsecase = mockk(relaxed = true)
    private val testStateManagerScope = TestStateManagerScope()

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        viewModel = SearchViewModel(initialState, mockUseCase, testStateManagerScope,testStateManagerScope.testDispatcher)
    }

    @Test
    fun `test search for keyword`() {
        // Arrange
        val keyword = "example"
        val resultList = listOf("example1", "example2")
        coEvery { mockUseCase(keyword) } returns flowOf(resultList)

        // Act
        viewModel.createTestContainer().test {
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
