package com.example.composerecipeapp.ui.main

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
import androidx.compose.runtime.getValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.composerecipeapp.core.logger.enableLogging
import com.example.composerecipeapp.platform.navigation.navigator.AppMainNavigation
import com.example.composerecipeapp.platform.navigation.navigator.AppMainNavigationFactory
import com.example.composerecipeapp.platform.navigation.navigator.AppNavHost
import com.example.composerecipeapp.platform.navigation.navigator.NavComposable
import com.example.composerecipeapp.platform.navigation.screens.*
import com.example.composerecipeapp.ui.main_view.BottomBar
import com.example.composerecipeapp.ui.main_view.BottomBarItems
import com.example.composerecipeapp.ui.main_view.MainScreen
import com.example.composerecipeapp.ui.main_view.NavigationView
import com.example.composerecipeapp.ui.provider.MainViewNavigator
import com.example.composerecipeapp.ui.provider.ParentNavHostController
import com.example.composerecipeapp.ui.recipe_detail.RecipeDetail
import com.example.composerecipeapp.ui.recipe_detail.RecipeDetailScreen
import com.example.composerecipeapp.ui.recipe_search.SearchBarContainer
import com.example.composerecipeapp.ui.recipe_videos.VideoPlayer
import com.example.composerecipeapp.ui.recipe_videos.VideoPlayerScreen
import com.example.composerecipeapp.ui.theme.ComposeRecipeAppTheme
import com.example.composerecipeapp.ui.user_interest.UserInterest
import com.example.composerecipeapp.ui.user_interest.UserInterestScreen
import com.example.composerecipeapp.util.observeState
import com.example.composerecipeapp.viewmodel.main.LoadSettings
import com.example.composerecipeapp.viewmodel.main.MainViewModel
import com.example.composerecipeapp.viewmodel.recipe_search.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    val mainViewModel by viewModels<MainViewModel>()

    @Inject
    lateinit var navigationFactory: AppMainNavigationFactory
    lateinit var appMainNavigation: AppMainNavigation

    @ExperimentalFoundationApi
    @ExperimentalMaterialApi
    @ExperimentalComposeUiApi
    @ExperimentalAnimationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableLogging = true
        mainViewModel.dispatch(LoadSettings)
        setContent {
            appMainNavigation = navigationFactory.create(rememberNavController())
            MainContent(
                mainViewModel = mainViewModel,
                appMainNavigation = appMainNavigation,
                appMainNavigationFactory = navigationFactory
            )
        }
    }
}

@ExperimentalFoundationApi
@ExperimentalAnimationApi
@ExperimentalComposeUiApi
@ExperimentalMaterialApi
@Composable
fun MainContent(
    mainViewModel: MainViewModel,
    appMainNavigation: AppMainNavigation,
    appMainNavigationFactory: AppMainNavigationFactory
) {
    val state = mainViewModel.observeState()
    AppCompatDelegate.setDefaultNightMode(
        if (state.isDarkModeOn) AppCompatDelegate.MODE_NIGHT_YES
        else AppCompatDelegate.MODE_NIGHT_NO
    )
    ComposeRecipeAppTheme(darkTheme = state.isDarkModeOn) {
        state.showLandingScreen?.let {
            MainApp(it, appMainNavigation, appMainNavigationFactory)
        }
    }
}

@ExperimentalFoundationApi
@ExperimentalMaterialApi
@ExperimentalComposeUiApi
@ExperimentalAnimationApi
@Composable
fun MainApp(
    showLandingScreen: Boolean,
    appMainNavigation: AppMainNavigation,
    appMainNavigationFactory: AppMainNavigationFactory
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
            MainScreen(appMainNavigationFactory)
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

