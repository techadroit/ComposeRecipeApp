package com.example.composerecipeapp.ui

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.composerecipeapp.R
import com.example.composerecipeapp.core.logger.enableLogging
import com.example.composerecipeapp.ui.main_view.BottomBar
import com.example.composerecipeapp.ui.main_view.BottomBarItems
import com.example.composerecipeapp.ui.main_view.NavigationView
import com.example.composerecipeapp.ui.navigation.NavigationDirections
import com.example.composerecipeapp.ui.provider.ParentNavHostController
import com.example.composerecipeapp.ui.recipe_detail.RecipeDetail
import com.example.composerecipeapp.ui.recipe_search.SearchBarContainer
import com.example.composerecipeapp.ui.recipe_videos.VideoPlayer
import com.example.composerecipeapp.viewmodel.recipe_search.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    @ExperimentalMaterialApi
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

@ExperimentalMaterialApi
@ExperimentalComposeUiApi
@ExperimentalAnimationApi
@Composable
fun MainApp() {
    val navController = rememberNavController()
    CompositionLocalProvider(ParentNavHostController provides navController) {
        NavHost(
            navController = navController,
            startDestination = NavigationDirections.mainDestination.destination
        ) {
            composable(NavigationDirections.mainDestination.destination) {
                AppContent()
            }
            composable(NavigationDirections.recipeDetailDestination.destination) {
                val id = it.arguments?.getString("recipe_id")
                id?.let {
                    RecipeDetail(id)
                }
            }
            composable(NavigationDirections.videoPlayer.destination) {
//                val id = it.arguments?.getString("youtube_id")
                    VideoPlayer()
            }
        }
    }

}

@ExperimentalMaterialApi
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
                    BottomBarItems(
                        NavigationDirections.savedRecipeDestination.destination,
                        "Saved Recipes"
                    ),
                    BottomBarItems(
                        NavigationDirections.recipeVideoDestination.destination,
                        "Videos"
                    ),
                )
            )
        },
        topBar = { SearchBarContainer(navController, searchViewModel = searchViewModel) },
        snackbarHost = { scaffoldState.snackbarHostState }
    ) {
        Box(modifier = Modifier.padding(it)){
            NavigationView(
                navController = navController,
                searchViewModel
            )
        }
    }
}

@ExperimentalMaterialApi
@ExperimentalComposeUiApi
@ExperimentalAnimationApi
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DefaultPreview() {
    ComposeRecipeAppTheme(darkTheme = false) {
        AppContent()
    }
}
