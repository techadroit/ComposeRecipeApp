package com.example.composerecipeapp.ui

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.composerecipeapp.core.logger.enableLogging
import com.example.composerecipeapp.ui.main_view.BottomBar
import com.example.composerecipeapp.ui.main_view.BottomBarItems
import com.example.composerecipeapp.ui.main_view.NavigationView
import com.example.composerecipeapp.ui.navigation.NavigationDirections
import com.example.composerecipeapp.ui.provider.ParentNavHostController
import com.example.composerecipeapp.ui.recipe_detail.RecipeDetail
import com.example.composerecipeapp.ui.recipe_search.SearchBarContainer
import com.example.composerecipeapp.ui.recipe_videos.VideoPlayer
import com.example.composerecipeapp.ui.theme.ComposeRecipeAppTheme
import com.example.composerecipeapp.ui.user_interest.UserInterest
import com.example.composerecipeapp.viewmodel.main.LoadSettings
import com.example.composerecipeapp.viewmodel.main.MainViewModel
import com.example.composerecipeapp.viewmodel.recipe_search.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    val mainViewModel by viewModels<MainViewModel>()

    @ExperimentalFoundationApi
    @ExperimentalMaterialApi
    @ExperimentalComposeUiApi
    @ExperimentalAnimationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        WindowCompat.setDecorFitsSystemWindows(window, false)
        enableLogging = true
        mainViewModel.dispatch(LoadSettings)
        setContent {
            MainContent(mainViewModel = mainViewModel)
        }
    }
}

@ExperimentalFoundationApi
@ExperimentalAnimationApi
@ExperimentalComposeUiApi
@ExperimentalMaterialApi
@Composable
fun MainContent(mainViewModel: MainViewModel) {
    val state = mainViewModel.stateEmitter.collectAsState().value
    AppCompatDelegate.setDefaultNightMode(
        if (state.isDarkModeOn) AppCompatDelegate.MODE_NIGHT_YES
        else AppCompatDelegate.MODE_NIGHT_NO
    )
    ComposeRecipeAppTheme(darkTheme = state.isDarkModeOn) {
        MainApp()
    }
}

@ExperimentalFoundationApi
@ExperimentalMaterialApi
@ExperimentalComposeUiApi
@ExperimentalAnimationApi
@Composable
fun MainApp() {
    val navController = rememberNavController()
    CompositionLocalProvider(ParentNavHostController provides navController) {
        NavHost(
            navController = navController,
            startDestination = NavigationDirections.userInterest.destination
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
                VideoPlayer()
            }
            composable(NavigationDirections.userInterest.destination) {
                UserInterest(navController)
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
                    BottomBarItems(NavigationDirections.settings.destination, "Settings")
                )
            )
        },
        topBar = { SearchBarContainer(navController, searchViewModel = searchViewModel) },
        snackbarHost = { scaffoldState.snackbarHostState }
    ) {
        Box(modifier = Modifier.padding(it)) {
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
