package com.feature.home.viewmodel

import com.appmattus.kotlinfixture.kotlinFixture
import com.domain.common.pojo.RecipeModel
import com.domain.recipe.cuisines.RecipeWithCuisine
import com.domain.recipe.cuisines.RecipesForSelectedCuisines
import com.feature.home.state.HomeRecipeState
import com.feature.home.state.RefreshHomeEvent
import com.feature.home.state.add
import com.feature.home.state.initialState
import com.feature.home.state.showLoading
import com.state_manager.events.AppEvent
import com.state_manager.extensions.verifyState
import com.state_manager.managers.Manager
import com.state_manager.scopes.StateManagerCoroutineScope
import com.state_manager.side_effects.SideEffect
import com.state_manager.state.AppState
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.isActive
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class HomeRecipeViewModelTest {

    val fixture = kotlinFixture()

    lateinit var viewModel: HomeRecipeViewModel
    @MockK
    lateinit var recipeWithCuisineUseCase: RecipesForSelectedCuisines

    val initialHomeState = HomeRecipeState()
    val recipeWithCuisine  by lazy {
        RecipeWithCuisine(
            cuisine = "Indian",
            recipeList = listOf(
            fixture<RecipeModel>()
            )
        )
    }

    @Before
    fun setUp(){
//        val testScheduler = TestCoroutineScheduler()
//        Dispatchers.setMain(StandardTestDispatcher(testScheduler))
        MockKAnnotations.init(this, relaxUnitFun = true)
        coEvery { recipeWithCuisineUseCase.invoke() } returns flowOf(recipeWithCuisine)
        viewModel = HomeRecipeViewModel(recipeWithCuisineUseCase,initialHomeState,TestStateManagerScope())
    }

    @After
    fun tearDown() {
//        Dispatchers.resetMain()
    }

//    @Test
//    fun `verify load recipe event`()= runTest{
//        val state = viewModel.initialState
//        viewModel.verifyState(LoadRecipeEvent){
//            println(it)
//            val states = listOf(
//                state.showLoading(true),
//                state.add(recipeWithCuisine)
//                    .showLoading(false) ,
//            )
//            println(states)
//            assert(it == states)
//        }
//    }

    @Test
    fun `verify refresh event`() = runTest{
        val state = viewModel.initialState
            val states = listOf(
                state.initialState(),
                state.showLoading(true),
                state.add(recipeWithCuisine)
                    .showLoading(false) ,
            )
            println(states)
        viewModel.verifyState(RefreshHomeEvent){
            assertEquals(states,it)
        }
//        viewModel.runCreate(CoroutineScope(UnconfinedTestDispatcher(testScheduler)))
//        val list = mutableListOf<HomeRecipeState>()
//
//        val job = launch(UnconfinedTestDispatcher()) {
//            viewModel.stateEmitter.toList(list)
//        }
//        viewModel.dispatch(RefreshHomeEvent)
//        assertEquals(states,list)
//        job.cancel()
    }
}

class TestStateManagerScope: StateManagerCoroutineScope {
    val testJob = Job()
    val testScope = TestScope(UnconfinedTestDispatcher() + testJob)
    override fun getScope(): CoroutineScope = testScope

    fun isCleared() = !testScope.isActive && testJob.isCancelled
}

