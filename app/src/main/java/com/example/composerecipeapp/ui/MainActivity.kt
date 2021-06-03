package com.example.composerecipeapp.ui

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.composerecipeapp.core.logger.enableLogging
import com.example.composerecipeapp.ui.provider.ParentNavHostController
import com.example.composerecipeapp.ui.recipe_detail.RecipeDetail
import com.example.composerecipeapp.ui.recipe_search.SearchBar
import com.example.composerecipeapp.ui.recipe_search.SearchViewModel
import com.example.composerecipeapp.ui.recipe_videos.VideoPlayer
import com.example.composerecipeapp.ui.recipes.BottomBar
import com.example.composerecipeapp.ui.recipes.BottomBarItems
import com.example.composerecipeapp.ui.recipes.NavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    @ExperimentalComposeUiApi
    @ExperimentalAnimationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        WindowCompat.setDecorFitsSystemWindows(window, false)
        enableLogging = true
        setContent {
            ComposeRecipeAppTheme {
                MainApp()
            }
        }
    }
}

@ExperimentalComposeUiApi
@ExperimentalAnimationApi
@Composable
fun MainApp() {
    val navController = rememberNavController()
    CompositionLocalProvider(ParentNavHostController provides navController) {
        NavHost(navController = navController, startDestination = "main_recipes") {
            composable("main_recipes") {
                AppContent()
            }
            composable("recipe_details/{recipe_id}") {
                val id = it.arguments?.getString("recipe_id")
                id?.let {
                    RecipeDetail(id)
                }
            }
            composable("recipe/videos/{youtube_id}") {
                val id = it.arguments?.getString("youtube_id")
                it?.let {
                    VideoPlayer()
                }
            }
        }
    }

}

@ExperimentalComposeUiApi
@ExperimentalAnimationApi
@Composable
fun AppContent() {
    val navController = rememberNavController()
    val searchViewModel: SearchViewModel = hiltViewModel()
    val scaffoldState = rememberScaffoldState()
    Scaffold(
        scaffoldState = scaffoldState,
        bottomBar = {
            BottomBar(
                navController = navController, items = listOf(
                    BottomBarItems("recipes/chicken", "Recipes"),
                    BottomBarItems("videos", "Videos"),
                )
            )
        },
        topBar = { SearchBar(navController, searchViewModel = searchViewModel) }
    ) {
        NavigationView(
            navController = navController,
            searchViewModel
        )
    }
}

@ExperimentalComposeUiApi
@ExperimentalAnimationApi
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DefaultPreview() {
    ComposeRecipeAppTheme(darkTheme = false) {
        AppContent()
    }
}
