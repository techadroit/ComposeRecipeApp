package com.example.composerecipeapp.domain

import com.example.composerecipeapp.BaseUnitTest
import com.example.composerecipeapp.core.exception.Failure
import com.example.composerecipeapp.data.network.response.SearchKey
import com.example.composerecipeapp.data.repositories.RecipeRepository
import com.example.composerecipeapp.domain.usecases.AutoCompleteUsecase
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import junit.framework.Assert.assertTrue
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class AutoCompleteUsecaseUnitTest : BaseUnitTest() {

    @MockK
    lateinit var recipeRepository: RecipeRepository

    lateinit var autoCompleteUsecase: AutoCompleteUsecase

    val sampleResponse = listOf<SearchKey>(
        SearchKey(1L, "Chicken", ""),
        SearchKey(2L, "Mutton", ""),
        SearchKey(3L, "Fish", ""),
    )

    @Before
    fun setUp() {
        autoCompleteUsecase = AutoCompleteUsecase(recipeRepository)
    }

    @Test
    fun testSampleResponse() {
        coEvery { recipeRepository.searchKeyword(any(), any()) } returns sampleResponse

        runTest {
            val response = autoCompleteUsecase("").first()
            assertTrue(response.size == 3)

            val searchKeyList = sampleResponse.map { it.title }
            assertTrue(response == searchKeyList)
        }
    }

    @Test
    fun testErrorResponse() {
        coEvery { recipeRepository.searchKeyword(any(), any()) }.throws(Failure.ServerError)
        runCatching {
            autoCompleteUsecase("")
        }.onFailure {
            assertTrue(it is Failure.ServerError)
        }
    }
}
