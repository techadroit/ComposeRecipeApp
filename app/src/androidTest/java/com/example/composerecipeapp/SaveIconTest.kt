package com.example.composerecipeapp

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import com.example.composerecipeapp.ui.ComposeRecipeAppTheme
import com.example.composerecipeapp.ui.views.SaveIcon
import org.junit.Rule
import org.junit.Test

class SaveIconTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun onSaveClickTest(){
        composeTestRule.setContent {
            ComposeRecipeAppTheme {
                SaveIcon(isSaved = false) {

                }
            }
        }

        composeTestRule.onNodeWithContentDescription("Not Saved").assertExists()
    }
}
