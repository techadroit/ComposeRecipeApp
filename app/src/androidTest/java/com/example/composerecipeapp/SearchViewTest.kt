package com.example.composerecipeapp

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.test.*
import com.feature.recipe.list.ui.SearchBar
import com.core.themes.ComposeRecipeAppTheme
import org.junit.Before
import org.junit.Test

class SearchViewTest : BaseTest() {

    @ExperimentalComposeUiApi
    @Before
    fun setUp() {
        composeTestRule.setContent {
            ComposeRecipeAppTheme {
                SearchBar(navigate = { }, dispatch = { }) {
                }
            }
        }
    }

    @Test
    fun searchBarLoaded() {
        composeTestRule.onNode(hasTestTag("search_bar")).assertExists()
    }

    @Test
    fun enterText() {
        composeTestRule.onNode(hasSetTextAction()).assertExists()
        composeTestRule.onNodeWithContentDescription("Back").assertExists()
        composeTestRule.onNode(hasSetTextAction()).performTextInput("Hello")
        composeTestRule.onNodeWithContentDescription("Search").assertExists()
    }
}
