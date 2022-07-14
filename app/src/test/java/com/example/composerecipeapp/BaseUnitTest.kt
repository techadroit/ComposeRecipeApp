package com.example.composerecipeapp

import io.mockk.MockKAnnotations
import org.junit.BeforeClass

abstract class BaseUnitTest {

//    @ExperimentalCoroutinesApi
//    protected val testCoroutineDispatcher = StandardTestDispatcher()
//
//    @ExperimentalCoroutinesApi
//    protected val testScope = TestScope(testCoroutineDispatcher)

    @BeforeClass
    fun b() {
        MockKAnnotations.init()
    }
}
