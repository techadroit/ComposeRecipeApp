package com.example.composerecipeapp.ui.main

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.core.navigtion.AppNavigator
import com.core.navigtion.navigator.AppNavHost
import com.core.themes.ComposeRecipeAppTheme
import com.core.themes.LocalAdaptiveSizeProvider
import com.core.themes.LocalDimensionProvider
import com.core.themes.WindowsSize
import com.core.themes.compactDimension
import com.core.themes.createDimension
import com.core.themes.expandedDimension
import com.core.themes.getAdaptiveSizes
import com.example.composerecipeapp.ui.main_view.MainScreen
import com.example.composerecipeapp.viewmodel.main.LoadSettings
import com.example.composerecipeapp.viewmodel.main.MainViewModel
import com.feature.common.observeState
import com.feature.recipe.detail.ui.RecipeDetailScreen
import com.feature.recipe.video.ui.VideoPlayerScreen
import com.feature.user.interest.ui.UserInterestScreen
import com.recipe.app.navigation.intent.MainViewIntent
import com.recipe.app.navigation.intent.UserInterestIntent
import com.recipe.app.navigation.provider.ParentNavHostController
import com.state_manager.logger.enableLogging
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


    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    @ExperimentalFoundationApi
    @ExperimentalAnimationApi
    @ExperimentalComposeUiApi
    @Composable
    fun MainContent(
        mainViewModel: MainViewModel,
        appMainNavigation: AppNavigator
    ) {
        val state = mainViewModel.observeState()
        val darkModeOn = state.isDarkModeOn ?: isSystemInDarkTheme()
        AppCompatDelegate.setDefaultNightMode(
            if (darkModeOn) AppCompatDelegate.MODE_NIGHT_YES
            else AppCompatDelegate.MODE_NIGHT_NO
        )

        val windowClass = calculateWindowSizeClass(activity = this)
        val adaptiveSizes = getAdaptiveSizes(windowClass)


        val dimension = when (adaptiveSizes?.windowSize) {
            WindowsSize.COMPACT -> createDimension()
            WindowsSize.MEDIUM -> compactDimension()
            WindowsSize.EXPANDED -> expandedDimension()
            else -> createDimension()
        }

        CompositionLocalProvider(
            LocalDimensionProvider provides dimension,
            LocalAdaptiveSizeProvider provides adaptiveSizes
        ) {
            ComposeRecipeAppTheme(darkTheme = darkModeOn) {
                state.showLandingScreen?.let {
                    MainApp(it, appMainNavigation)
                }
            }
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

