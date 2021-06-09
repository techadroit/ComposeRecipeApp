package com.example.composerecipeapp

import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.example.composerecipeapp.ui.ComposeRecipeAppTheme
import com.example.composerecipeapp.ui.pojo.RecipeModel
import com.example.composerecipeapp.ui.recipe_list.RecipeList
import org.junit.Test

class RecipeListTest : BaseTest() {

    val recipeList: List<RecipeModel> =
        listOf(RecipeModel(1, "Recipe", 45, "", 10, false))

    fun launchApp(list: List<RecipeModel>, showPagination: Boolean = false) {
        composeTestRule.setContent {
            ComposeRecipeAppTheme {
                RecipeList(
                    recipeList = list,
                    dispatch = {},
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
    }

    @Test
    fun onRemoveFromSaveClick() {

        val recipeList: List<RecipeModel> =
            listOf(RecipeModel(1, "Recipe", 45, "", 10, true))
        launchApp(recipeList)

        composeTestRule.onNodeWithContentDescription("Saved").performClick()
        composeTestRule.onNodeWithContentDescription("Not Saved").assertExists()
    }

    @Test
    fun paginationExist() {

        val recipeList: List<RecipeModel> =
            listOf(RecipeModel(1, "Recipe", 45, "", 10, true))
        launchApp(recipeList, true)
        composeTestRule.onNode(hasTestTag("pagination_loading")).assertExists()
    }
}
