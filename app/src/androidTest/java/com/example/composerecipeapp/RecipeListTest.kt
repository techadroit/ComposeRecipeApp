package com.example.composerecipeapp

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.example.composerecipeapp.ui.pojo.RecipeModel
import com.example.composerecipeapp.ui.recipe_list.RecipeList
import com.example.composerecipeapp.ui.theme.ComposeRecipeAppTheme
import com.example.composerecipeapp.viewmodel.recipe_list.RecipeListViewmodel
import com.example.composerecipeapp.viewmodel.recipe_list.RemoveSavedRecipeEvent
import com.example.composerecipeapp.viewmodel.recipe_list.SaveRecipeEvent
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import org.junit.Before
import org.junit.Test

class RecipeListTest : BaseTest() {

    val recipeList: List<RecipeModel> =
        listOf(RecipeModel(1, "Recipe", 45, "", 10, false))
    @MockK
    lateinit var viewmodel: RecipeListViewmodel

    @Before
    fun setUp() {
        every { viewmodel.dispatch(any()) } returns Unit
    }

    @ExperimentalMaterialApi
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

    @ExperimentalMaterialApi
    @Test
    fun listLoaded() {
        launchApp(recipeList)
        composeTestRule.onNodeWithText("Recipe").assertExists()
    }

    @ExperimentalMaterialApi
    @Test
    fun onSaveClick() {
        launchApp(recipeList)
        composeTestRule.onNodeWithContentDescription("Not Saved").performClick()
        composeTestRule.onNodeWithContentDescription("Saved").assertExists()
        verify { viewmodel.dispatch(ofType(SaveRecipeEvent::class)) }
    }

    @ExperimentalMaterialApi
    @Test
    fun onRemoveFromSaveClick() {

        val recipeList: List<RecipeModel> =
            listOf(RecipeModel(1, "Recipe", 45, "", 10, true))
        launchApp(recipeList)

        composeTestRule.onNodeWithContentDescription("Saved").performClick()
        composeTestRule.onNodeWithContentDescription("Not Saved").assertExists()
        verify { viewmodel.dispatch(ofType(RemoveSavedRecipeEvent::class)) }
    }

    @ExperimentalMaterialApi
    @Test
    fun paginationExist() {

        val recipeList: List<RecipeModel> =
            listOf(RecipeModel(1, "Recipe", 45, "", 10, true))
        launchApp(recipeList, true)
        composeTestRule.onNode(hasTestTag("pagination_loading")).assertExists()
    }
}
