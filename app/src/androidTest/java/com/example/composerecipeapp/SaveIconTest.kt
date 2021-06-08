package com.example.composerecipeapp

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.performClick
import com.example.composerecipeapp.ui.ComposeRecipeAppTheme
import com.example.composerecipeapp.ui.views.SaveIcon
import org.junit.Rule
import org.junit.Test

class SaveIconTest : BaseTest() {

    @Test
    fun onSaveClickTest(){
        composeTestRule.setContent {
            ComposeRecipeAppTheme {
                SaveIcon(isSaved = false) {
                }
            }
        }

        composeTestRule.onNodeWithContentDescription("Not Saved").assertExists()
        composeTestRule.onNodeWithContentDescription("Not Saved").performClick()
        composeTestRule.onNodeWithContentDescription("Saved").assertExists()
        composeTestRule.onNodeWithContentDescription("Not Saved").assertDoesNotExist()
    }
}
