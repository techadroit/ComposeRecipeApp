package com.example.composerecipeapp

import io.mockk.MockKAnnotations
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import org.junit.Before

abstract class BaseUnitTest {

    @ExperimentalCoroutinesApi
    protected val testCoroutineDispatcher = TestCoroutineDispatcher()

    @ExperimentalCoroutinesApi
    protected val testScope = TestCoroutineScope(testCoroutineDispatcher)

    @Before
    fun initMockk() {
        MockKAnnotations.init(this)
    }
}
