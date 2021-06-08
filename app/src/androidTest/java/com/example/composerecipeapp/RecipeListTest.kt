package com.example.composerecipeapp

import com.example.composerecipeapp.ui.ComposeRecipeAppTheme
import org.junit.Test

class RecipeListTest : BaseTest(){

    @Test
    fun appLaunches(){
        composeTestRule.setContent {
            ComposeRecipeAppTheme {

            }
        }
    }

}
