package com.example.composerecipeapp.ui.main_view

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.rememberNavController
import com.core.navigtion.AppNavigator
import com.core.navigtion.navigator.NavComposable
import com.core.themes.LocalAdaptiveSizeProvider
import com.core.themes.WindowsSize
import com.feature.recipe.list.ui.SearchBarContainer
import com.feature.recipe.list.viewmodel.SearchViewModel
import com.recipe.app.navigation.intent.HomeViewIntent
import com.recipe.app.navigation.intent.MainViewIntent
import com.recipe.app.navigation.intent.RecipeVideoListIntent
import com.recipe.app.navigation.intent.SavedRecipeIntent
import com.recipe.app.navigation.intent.SettingsViewIntent
import com.recipe.app.navigation.provider.MainViewNavigator
import com.recipe.app.navigation.provider.ParentNavHostController

@OptIn(
    ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class,
    ExperimentalComposeUiApi::class, ExperimentalAnimationApi::class
)
fun NavGraphBuilder.MainScreen() {
    NavComposable(MainViewIntent()) {
        val mainViewNavigator = AppNavigator.create(rememberNavController())
        CompositionLocalProvider(MainViewNavigator provides mainViewNavigator) {
            AppContent()
        }
    }
}

val mainContentNavigation = listOf(
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

@OptIn(ExperimentalMaterial3Api::class)
@ExperimentalFoundationApi
@ExperimentalComposeUiApi
@ExperimentalAnimationApi
@Composable
fun AppContent() {
    val topLevelNavigator = ParentNavHostController.current
    val searchViewModel: SearchViewModel = hiltViewModel()
    Scaffold(
//        bottomBar = {
//            BottomBar(
//                items = listOf(
//                    BottomBarItems(HomeViewIntent.getScreenName(), "Home"),
//                    BottomBarItems(
//                        SavedRecipeIntent.getScreenName(),
//                        "Saved Recipes"
//                    ),
//                    BottomBarItems(
//                        RecipeVideoListIntent.getScreenName(),
//                        "Videos"
//                    ),
//                    BottomBarItems(SettingsViewIntent.getScreenName(), "Settings")
//                )
//            )
//        },
        topBar = {
            if (topLevelNavigator.getCurrentRoute()
                != SettingsViewIntent.getScreenName()
            )
                SearchBarContainer(searchViewModel = searchViewModel)
        },
    ) {
        Box(modifier = Modifier.padding(it)) {
            RecipeNavHost(searchViewModel)
        }
    }
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalAnimationApi::class)
@Composable
fun RecipeNavHost(searchViewModel: SearchViewModel) {
    val adaptiveSizes = LocalAdaptiveSizeProvider.current
    Row(modifier = Modifier.fillMaxHeight()) {
        if (adaptiveSizes.windowSize != WindowsSize.COMPACT)
            NavRail(items = mainContentNavigation)
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Box(modifier = Modifier.weight(1f)) {
                NavigationView(searchViewModel = searchViewModel)
            }
            if (adaptiveSizes.windowSize == WindowsSize.COMPACT)
                BottomBar(items = mainContentNavigation)
        }
    }
}


