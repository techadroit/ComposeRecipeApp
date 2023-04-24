package com.example.composerecipeapp.ui.main_view

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.rememberNavController
import com.example.composerecipeapp.platform.navigation.navigator.AppMainNavigationFactory
import com.example.composerecipeapp.platform.navigation.navigator.NavComposable
import com.example.composerecipeapp.ui.destinations.*
import com.example.composerecipeapp.ui.provider.MainViewNavigator
import com.example.composerecipeapp.ui.provider.ParentNavHostController
import com.example.composerecipeapp.ui.recipe_search.SearchBarContainer
import com.example.composerecipeapp.viewmodel.recipe_search.SearchViewModel

@OptIn(
    ExperimentalFoundationApi::class, ExperimentalMaterialApi::class,
    ExperimentalComposeUiApi::class, ExperimentalAnimationApi::class
)
fun NavGraphBuilder.MainScreen(
    appMainNavigationFactory: AppMainNavigationFactory
) {
    NavComposable(MainViewIntent()) {
        val mainViewNavigator = appMainNavigationFactory.create(rememberNavController())
        CompositionLocalProvider(MainViewNavigator provides mainViewNavigator) {
            AppContent()
        }
    }
}

@ExperimentalFoundationApi
@ExperimentalMaterialApi
@ExperimentalComposeUiApi
@ExperimentalAnimationApi
@Composable
fun AppContent() {
    val topLevelNavigator = ParentNavHostController.current
    val searchViewModel: SearchViewModel = hiltViewModel()
    val scaffoldState = rememberScaffoldState()
    Scaffold(
        scaffoldState = scaffoldState,
        bottomBar = {
            BottomBar(
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
            if (topLevelNavigator.getCurrentRoute()
                != SettingsViewIntent.getScreenName()
            )
                SearchBarContainer(searchViewModel = searchViewModel)
        },
        snackbarHost = { scaffoldState.snackbarHostState }
    ) {
        Box(modifier = Modifier.padding(it)) {
            NavigationView(searchViewModel)
        }
    }
}
