package com.example.composerecipeapp

import io.mockk.MockKAnnotations
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import org.junit.Before

abstract class BaseUnitTest {

    @ExperimentalCoroutinesApi
    protected val testCoroutineDispatcher = StandardTestDispatcher()

    @ExperimentalCoroutinesApi
    protected val testScope = TestScope(testCoroutineDispatcher)

    @Before
    fun initMocks() = MockKAnnotations.init(this, relaxUnitFun = true)
}
