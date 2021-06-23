package com.example.composerecipeapp

import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.performClick
import com.example.composerecipeapp.ui.views.SaveIcon
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import org.junit.Before
import org.junit.Test

class SaveIconTest : BaseTest() {

    @MockK
    lateinit var lambdaSlot: (Boolean) -> Unit

    @Before
    fun setUp(){
        every { lambdaSlot.invoke(any()) } returns Unit
    }

    @Test
    fun verifySaveEventCalled(){
        useView {
            SaveIcon(isSaved = false) {
               lambdaSlot.invoke(it)
            }
        }

        composeTestRule.onNodeWithContentDescription("Not Saved").assertExists()
        composeTestRule.onNodeWithContentDescription("Not Saved").performClick()
        composeTestRule.onNodeWithContentDescription("Saved").assertExists()
        composeTestRule.onNodeWithContentDescription("Not Saved").assertDoesNotExist()

        verify { lambdaSlot.invoke(true) }
    }

    @Test
    fun verifyRemoveEventCalled(){
        useView {
            SaveIcon(isSaved = true) {
               lambdaSlot.invoke(it)
            }
        }

        composeTestRule.onNodeWithContentDescription("Saved").assertExists()
        composeTestRule.onNodeWithContentDescription("Saved").performClick()
        composeTestRule.onNodeWithContentDescription("Not Saved").assertExists()
        composeTestRule.onNodeWithContentDescription("Saved").assertDoesNotExist()

        verify { lambdaSlot.invoke(false) }
    }
}
