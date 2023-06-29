package com.state_manager.viewmodel_test

import com.state_manager.BaseUnitTest
import com.state_manager.TestStateManagerScope
import com.state_manager.extensions.verifyState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.withContext
import org.junit.Before
import org.junit.Test

internal class StateEventManagerTest : BaseUnitTest() {
    lateinit var viewModel: TestViewModel
    private val initialState = TestState()
    private val testStateManagerScope = TestStateManagerScope()

    @ExperimentalCoroutinesApi
    @Before
    fun setUp() {
        viewModel = TestViewModel(initialTestState = initialState, testStateManagerScope)
    }

//    @ExperimentalCoroutinesApi
//    @Test
//    fun checkInitialState() {
////        runTest {
////            val list = mutableListOf<TestState>()
////            backgroundScope.launch {
////                viewModel.stateEmitter.toList(list)
////            }
////            println(list)
////            assert(list.isNotEmpty())
////        }
//        runBlocking {
//            val list = mutableListOf<TestState>()
//
//            val job = launch {
//                viewModel.stateEmitter.toList(list)
//            }
//            viewModel.dispatch(IncrementCountEvent(1))
//            println(list)
//            job.cancel()
//            assert(list.isNotEmpty())
//        }
//    }

//    @ExperimentalCoroutinesApi
//    @Test
//    fun checkListOfStates() {
//        runTest {
//            viewModel.verifyState(
//                IncrementCountEvent(1),
//                IncrementCountEvent(1)
//            ) {
//                print(it)
//                assert(it.isNotEmpty())
//            }
//        }
//    }

    @ExperimentalCoroutinesApi
    @Test
    fun checkListOfStates2() {
        runTest {
            withContext(Dispatchers.Default.limitedParallelism(1)) {
                // delays are not skipped here

                 backgroundScope.launch {
                    viewModel.stateEmitter.collect {
                        println(it)
                    }
                }
                viewModel.dispatch(IncrementCountEvent(5))
//                delay(10)
//                viewModel.dispatch(IncrementCountEvent(5))
                delay(300)
//                job.join()
//                job.cancel()
            }
        }
//        runTest {
//
//            viewModel.dispatch(IncrementCountEvent(5))
//            backgroundScope.launch {
//                viewModel.stateEmitter.collect {
//                    println(it)
//                }
//            }
//            runCurrent()
//        }
//            viewModel.verifyState(
//                IncrementCountEvent(5),
//            ) {
//                print(it)
//                assert(it == listOf(TestState(), TestState(0,true), TestState(5)))
//            }
    }

//    @ExperimentalCoroutinesApi
//    @Test
//    fun checkListOfStates3() {
//        runTest {
//            viewModel.verifyState(
//                DecrementCountEvent(5),
//                IncrementCountEvent(10)
//            ) {
//                print(it)
//                assert(it == listOf(TestState(-5), TestState(5)))
//            }
//        }
//    }

//    @Test
//    fun clearTest() {
//        viewModel.clear()
//        assert(testStateManagerScope.isCleared())
//    }
}

