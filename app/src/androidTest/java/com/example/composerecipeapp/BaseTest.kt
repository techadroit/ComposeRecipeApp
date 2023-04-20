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

//    /**
//     * Manages the components' state and is used to perform injection on your test
//     */
//    @get:Rule(order = 0)
//    val hiltRule = HiltAndroidRule(this)
//
//    /**
//     * Create a temporary folder used to create a Data Store file. This guarantees that
//     * the file is removed in between each test, preventing a crash.
//     */
//    @BindValue
//    @get:Rule(order = 1)
//    val tmpFolder: TemporaryFolder = TemporaryFolder.builder().assureDeletion().build()

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
