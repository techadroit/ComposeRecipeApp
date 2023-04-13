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
import com.example.composerecipeapp.ui.main_view.NavigationView
import com.example.composerecipeapp.ui.provider.ParentNavHostController
import com.example.composerecipeapp.ui.recipe_detail.RecipeDetail
import com.example.composerecipeapp.ui.recipe_search.SearchBarContainer
import com.example.composerecipeapp.ui.recipe_search.SearchView
import com.example.composerecipeapp.ui.recipe_videos.VideoPlayer
import com.example.composerecipeapp.ui.theme.ComposeRecipeAppTheme
import com.example.composerecipeapp.ui.user_interest.UserInterest
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
    val navController = rememberNavController()
    CompositionLocalProvider(ParentNavHostController provides navController) {
        AppNavHost(
            navigator = appMainNavigation,
            startDestination = if (showLandingScreen)
                UserInterestIntent()
            else
                MainViewIntent()
        ) {
            NavComposable(UserInterestIntent()) {
                UserInterest(appMainNavigation)
            }
            NavComposable(MainViewIntent()) {
                AppContent(appMainNavigation, appMainNavigationFactory)
            }
            NavComposable(RecipeDetailIntent()) {
                val arguments = RecipeDetailIntent.getArguments(it.arguments)
                RecipeDetail(recipeId = arguments.recipeId)
            }
            NavComposable(VideoPlayerIntent()) {
                VideoPlayer()
            }
        }
    }
}
//@ExperimentalFoundationApi
//@ExperimentalMaterialApi
//@ExperimentalComposeUiApi
//@ExperimentalAnimationApi
//@Composable
//fun MainApp(showLandingScreen: Boolean) {
//    val navController = rememberNavController()
//    CompositionLocalProvider(ParentNavHostController provides navController) {
//        NavHost(
//            navController = navController,
//            startDestination = if (showLandingScreen)
//                NavigationDirections.userInterest.destination
//            else
//                NavigationDirections.mainDestination.destination
//        ) {
//            composable(NavigationDirections.mainDestination.destination) {
//                AppContent()
//            }
//            composable(NavigationDirections.recipeDetailDestination.destination) {
//                val id = it.arguments?.getString("recipe_id")
//                id?.let {
//                    RecipeDetail(id)
//                }
//            }
//            composable(NavigationDirections.videoPlayer.destination) {
//                VideoPlayer()
//            }
//            composable(NavigationDirections.userInterest.destination) {
//                UserInterest(navController)
//            }
//        }
//    }
//}

@ExperimentalFoundationApi
@ExperimentalMaterialApi
@ExperimentalComposeUiApi
@ExperimentalAnimationApi
@Composable
fun AppContent(
    parentNavigation: AppMainNavigation,
    appMainNavigationFactory: AppMainNavigationFactory
) {
    val appMainNavigation = appMainNavigationFactory.create(rememberNavController())
    val searchViewModel: SearchViewModel = hiltViewModel()
    val scaffoldState = rememberScaffoldState()
    Scaffold(
        scaffoldState = scaffoldState,
        bottomBar = {
            BottomBar(
                appMainNavigation = appMainNavigation,
                items = listOf(
                    BottomBarItems(HomeViewIntent.getScreenName(), "Home"),
                    BottomBarItems(
                        SavedRecipeIntent.getScreenName(),
                        "Saved Recipes"
                    ),
                    BottomBarItems(
                        RecipeVideoListIntent.getScreenName(),
                        "Videos"
                    ),
                    BottomBarItems(SettingsViewIntent.getScreenName(), "Settings")
                )
            )
        },
        topBar = {
            if (appMainNavigation.getCurrentRoute()
                != SettingsViewIntent.getScreenName()
            )
                SearchBarContainer(
                    parentNavigation,
                    appMainNavigation,
                    searchViewModel = searchViewModel
                )
        },
        snackbarHost = { scaffoldState.snackbarHostState }
    ) {
        Box(modifier = Modifier.padding(it)) {
            NavigationView(
                navController = parentNavigation,
                searchViewModel,
                appMainNavigation = appMainNavigation,
            )
        }
    }
}

@Composable
fun currentRoute(navController: NavHostController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route
}

@ExperimentalFoundationApi
@ExperimentalMaterialApi
@ExperimentalComposeUiApi
@ExperimentalAnimationApi
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DefaultPreview() {
    ComposeRecipeAppTheme(darkTheme = false) {
//        AppContent()
    }
}
