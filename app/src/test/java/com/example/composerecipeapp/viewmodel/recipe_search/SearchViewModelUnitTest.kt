package com.example.composerecipeapp.viewmodel.recipe_search

import com.example.composerecipeapp.BaseUnitTest
import com.example.composerecipeapp.domain.usecases.AutoCompleteUsecase
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test


class SearchViewModelUnitTest : BaseUnitTest() {

    private val initialState = SearchState()
    @MockK
    lateinit var useCase: AutoCompleteUsecase
    lateinit var searchViewModel: SearchViewModel
    private val response = listOf("Chicken", "Mutton", "Fish")

    @Before
    fun setUp() {
        searchViewModel = SearchViewModel(initialState, useCase)
    }

    @Test
    fun testAutoComplete() {
        coEvery { useCase(any()) } returns flow {
            emit(response)
        }
        runTest {
            searchViewModel.runStateTest(SearchTextEvent("Chicken")) { _, s ->
                assertTrue(s.list == response)
            }
        }
    }

}
