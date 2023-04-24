package com.example.composerecipeapp

import androidx.compose.runtime.Composable
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.composerecipeapp.ui.main.MainActivity
import com.example.composerecipeapp.ui.theme.ComposeRecipeAppTheme
import io.mockk.MockKAnnotations
import org.junit.Before
import org.junit.Rule
import org.junit.rules.TemporaryFolder
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
abstract class BaseTest {

    /**
     * Use the primary activity to initialize the app normally.
     */
    @get:Rule(order = 2)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun initializeMock() {
        MockKAnnotations.init(this)
    }

    fun useView(content: @Composable () -> Unit) {
        composeTestRule.setContent {
            ComposeRecipeAppTheme {
                content.invoke()
            }
        }
    }
}
