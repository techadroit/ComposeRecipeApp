package com.example.composerecipeapp

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.runner.AndroidJUnitRunner
import org.junit.Rule

abstract class BaseTest{

    @get:Rule
    val composeTestRule = createComposeRule()
}
