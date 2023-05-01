package com.example.composerecipeapp.ui.main

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.core.navigtion.AppNavigator
import com.core.navigtion.navigator.AppNavHost
import com.example.composerecipeapp.core.logger.enableLogging
import com.example.composerecipeapp.ui.destinations.MainViewIntent
import com.example.composerecipeapp.ui.destinations.UserInterestIntent
import com.example.composerecipeapp.ui.main_view.MainScreen
import com.example.composerecipeapp.ui.provider.ParentNavHostController
import com.example.composerecipeapp.ui.recipe_detail.RecipeDetailScreen
import com.example.composerecipeapp.ui.recipe_videos.VideoPlayerScreen
import com.example.composerecipeapp.ui.theme.ComposeRecipeAppTheme
import com.example.composerecipeapp.ui.user_interest.UserInterestScreen
import com.example.composerecipeapp.util.observeState
import com.example.composerecipeapp.viewmodel.main.LoadSettings
import com.example.composerecipeapp.viewmodel.main.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    val mainViewModel by viewModels<MainViewModel>()

    @ExperimentalFoundationApi
    @ExperimentalComposeUiApi
    @ExperimentalAnimationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableLogging = true
        mainViewModel.dispatch(LoadSettings)
        setContent {
            val appMainNavigation = AppNavigator.create(rememberNavController())
            MainContent(
                mainViewModel = mainViewModel,
                appMainNavigation = appMainNavigation
            )
        }
    }
}

@ExperimentalFoundationApi
@ExperimentalAnimationApi
@ExperimentalComposeUiApi
@Composable
fun MainContent(
    mainViewModel: MainViewModel,
    appMainNavigation: AppNavigator
) {
    val state = mainViewModel.observeState()
    AppCompatDelegate.setDefaultNightMode(
        if (state.isDarkModeOn) AppCompatDelegate.MODE_NIGHT_YES
        else AppCompatDelegate.MODE_NIGHT_NO
    )
    ComposeRecipeAppTheme(darkTheme = state.isDarkModeOn) {
        state.showLandingScreen?.let {
            MainApp(it, appMainNavigation)
        }
    }
}

@ExperimentalFoundationApi
@ExperimentalComposeUiApi
@ExperimentalAnimationApi
@Composable
fun MainApp(
    showLandingScreen: Boolean,
    appMainNavigation: AppNavigator,
) {
    CompositionLocalProvider(ParentNavHostController provides appMainNavigation) {
        AppNavHost(
            navigator = appMainNavigation,
            startDestination = if (showLandingScreen)
                UserInterestIntent()
            else
                MainViewIntent()
        ) {
            UserInterestScreen()
            MainScreen()
            RecipeDetailScreen()
            VideoPlayerScreen()
        }
    }
}

@Composable
fun currentRoute(navController: NavHostController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route
}

