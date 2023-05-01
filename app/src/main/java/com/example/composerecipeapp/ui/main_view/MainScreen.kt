package com.example.composerecipeapp.ui.main_view

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
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
import com.example.composerecipeapp.ui.destinations.*
import com.example.composerecipeapp.ui.provider.MainViewNavigator
import com.example.composerecipeapp.ui.provider.ParentNavHostController
import com.example.composerecipeapp.ui.recipe_search.SearchBarContainer
import com.example.composerecipeapp.viewmodel.recipe_search.SearchViewModel

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

@OptIn(ExperimentalMaterial3Api::class)
@ExperimentalFoundationApi
@ExperimentalComposeUiApi
@ExperimentalAnimationApi
@Composable
fun AppContent() {
    val topLevelNavigator = ParentNavHostController.current
    val searchViewModel: SearchViewModel = hiltViewModel()
    Scaffold(
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
    ) {
        Box(modifier = Modifier.padding(it)) {
            NavigationView(searchViewModel)
        }
    }
}
