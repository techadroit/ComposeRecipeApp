package com.example.composerecipeapp


import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.domain.common.pojo.RecipeModel
import com.example.composerecipeapp.ui.recipe_list.RecipeList
import com.core.themes.ComposeRecipeAppTheme
import com.feature.recipe.list.state.RemoveSavedRecipeEvent
import com.feature.recipe.list.state.SaveRecipeEvent
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import org.junit.Before
import org.junit.Test

class RecipeListTest : BaseTest() {

    val recipeList: List<RecipeModel> =
        listOf(RecipeModel(1, "Recipe", 45, "", 10, false))

    @MockK
    lateinit var viewmodel: com.feature.recipe.list.viewmodel.RecipeListViewmodel

    @Before
    fun setUp() {
        every { viewmodel.dispatch(any()) } returns Unit
    }

    fun launchApp(list: List<RecipeModel>, showPagination: Boolean = false) {
        composeTestRule.setContent {
            ComposeRecipeAppTheme {
                RecipeList(
                    recipeList = list,
                    dispatch = {
                        viewmodel.dispatch(it)
                    },
                    navigate = {},
                    showPaginationLoading = showPagination,
                    keyword = "chicken",
                    endOfList = false
                )
            }
        }
    }

    @Test
    fun listLoaded() {
        launchApp(recipeList)
        composeTestRule.onNodeWithText("Recipe").assertExists()
    }

    @Test
    fun onSaveClick() {
        launchApp(recipeList)
        composeTestRule.onNodeWithContentDescription("Not Saved").performClick()
        composeTestRule.onNodeWithContentDescription("Saved").assertExists()
        verify { viewmodel.dispatch(ofType(SaveRecipeEvent::class)) }
    }

    @Test
    fun onRemoveFromSaveClick() {

        val recipeList: List<RecipeModel> =
            listOf(RecipeModel(1, "Recipe", 45, "", 10, true))
        launchApp(recipeList)

        composeTestRule.onNodeWithContentDescription("Saved").performClick()
        composeTestRule.onNodeWithContentDescription("Not Saved").assertExists()
        verify { viewmodel.dispatch(ofType(RemoveSavedRecipeEvent::class)) }
    }

    @Test
    fun paginationExist() {

        val recipeList: List<RecipeModel> =
            listOf(RecipeModel(1, "Recipe", 45, "", 10, true))
        launchApp(recipeList, true)
        composeTestRule.onNode(hasTestTag("pagination_loading")).assertExists()
    }
}
