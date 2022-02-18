package com.example.composerecipeapp

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope

abstract class BaseUnitTest {

    @ExperimentalCoroutinesApi
    protected val testCoroutineDispatcher = StandardTestDispatcher()

    @ExperimentalCoroutinesApi
    protected val testScope = TestScope(testCoroutineDispatcher)
}
