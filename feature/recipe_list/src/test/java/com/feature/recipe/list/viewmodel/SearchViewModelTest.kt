package com.feature.recipe.list.viewmodel

import com.domain.recipe.search.AutoCompleteUsecase
import com.feature.recipe.list.state.SearchState
import com.feature.recipe.list.state.SearchTextEvent
import com.state_manager.extensions.createTestContainer
import com.state_manager.test.TestStateManagerScope
import com.state_manager.test.expect
import com.state_manager.test.test
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import org.junit.Before
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
        viewModel = SearchViewModel(initialState, mockUseCase, testStateManagerScope)
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
