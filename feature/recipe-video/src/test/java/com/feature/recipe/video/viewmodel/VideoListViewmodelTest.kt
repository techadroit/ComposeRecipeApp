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
    private val testStateManagerScope = TestStateManagerScope()
//    private val testDispatcher = rule.dispatcher

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        viewModel = VideoListViewModel(
            initialState,
            mockSearchVideoRecipeUsecase,
            rule.dispatcher
        )
    }

    @Test
    fun `test load videos`() {
        // Arrange
        val query = "example"
        val isPaginate = false
        coEvery { mockSearchVideoRecipeUsecase(any()) } returns flowOf(videoList)

        // Act
        viewModel.createTestContainer().test {
            forEvents(LoadVideos(isPaginate,query))
            verify {
                expect(
                    initialState,
                    initialState.onLoading(isPaginate = isPaginate).setQuery(query),
                    initialState
                        .onLoading(isPaginate = isPaginate).setQuery(query)
                        .onSuccess(videoList, isPaginate = isPaginate)
                )
            }
        }
    }

    @Test
    fun `test refresh video screen`() {
        // Arrange
        val query = "example"
        val isPaginate = false
        coEvery { mockSearchVideoRecipeUsecase(any()) } returns flowOf(videoList)
        val initialState = RecipeVideoState().setQuery(query)
        // Act
        viewModel.createTestContainer().test {
            withState(initialState)
            forEvents(RefreshVideoScreen)
            verify {
                expect(
                    initialState,
                    initialState.onLoading(false),
                    initialState.onLoading(isPaginate = isPaginate).setQuery(query),
                    initialState
                        .onLoading(isPaginate = isPaginate).setQuery(query)
                        .onSuccess(videoList, isPaginate = isPaginate)
                )
            }
        }
    }
}
