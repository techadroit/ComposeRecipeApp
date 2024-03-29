package com.feature.recipe.video.viewmodel

import com.appmattus.kotlinfixture.kotlinFixture
import com.domain.common.pojo.VideoRecipeModel
import com.domain.recipe.video.SearchVideoRecipeUsecase
import com.feature.recipe.video.state.LoadVideos
import com.feature.recipe.video.state.RecipeVideoState
import com.feature.recipe.video.state.RefreshVideoScreen
import com.feature.recipe.video.state.onLoading
import com.feature.recipe.video.state.onSuccess
import com.feature.recipe.video.state.setQuery
import com.state_manager.extensions.createTestContainer
import com.state_manager.test.StateManagerTestRule
import com.state_manager.test.TestStateManagerScope
import com.state_manager.test.expect
import com.state_manager.test.test
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class VideoListViewmodelTest {

    @get:Rule
    var rule = StateManagerTestRule()
    val fixture = kotlinFixture()
    val videoList = fixture<List<VideoRecipeModel>>()

    private lateinit var viewModel: VideoListViewModel
    private val initialState = RecipeVideoState()

    @MockK
    lateinit var mockSearchVideoRecipeUsecase: SearchVideoRecipeUsecase

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        coEvery { mockSearchVideoRecipeUsecase(any()) } returns flowOf(videoList)
        viewModel = VideoListViewModel(
            initialState,
            mockSearchVideoRecipeUsecase,
            UnconfinedTestDispatcher()
        )
    }

    @Test
    fun `test load videos`() {
        // Arrange
        val query = "chicken"
        val isPaginate = false

        val initialState = viewModel.initialState
        val states = listOf(
            initialState,
            initialState.onLoading(isPaginate = isPaginate).setQuery(query),
            initialState.onLoading(isPaginate = isPaginate).setQuery(query)
                .onSuccess(videoList, isPaginate = isPaginate)
        )

        // Act
        viewModel.createTestContainer().test {
            withState(initialState)
            forEvents(LoadVideos(isPaginate, query))
            verify {
                expect(
                    states
                )
            }
        }
    }

    @Test
    fun `test refresh video screen`() {
        // Arrange
        val query = "chicken"
        val isPaginate = false

        val initialState = RecipeVideoState().setQuery(query)
        val states = listOf(
            initialState,
            RecipeVideoState().onLoading(false),
            initialState
                .onLoading(isPaginate = isPaginate)
                .setQuery(query),
            initialState
                .onLoading(isPaginate = isPaginate)
                .setQuery(query)
                .onSuccess(videoList, isPaginate = isPaginate)
        )
        // Act
        viewModel.createTestContainer().test {
            withState(initialState)
            forEvents(RefreshVideoScreen)
            verify {
                expect(states)
            }
        }
    }

}
