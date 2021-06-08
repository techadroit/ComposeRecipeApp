package com.example.composerecipeapp

import androidx.compose.ui.test.junit4.createComposeRule
import org.junit.Rule

@HiltAndroidTest
abstract class BaseTest {

    @get:Rule(order = 1)
    var hiltTestRule = HiltAndroidRule(this)

    @get:Rule(order = 2)
    val composeTestRule = createComposeRule()
}
