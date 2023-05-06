package com.example.composerecipeapp.ui.main_view

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.core.navigtion.navigator.AppNavHost
import com.core.navigtion.navigator.NavComposable
import com.example.composerecipeapp.ui.home_view.HomeView
import com.feature.recipe.list.ui.SearchView
import com.feature.recipe.list.ui.RecipeListScreen
import com.feature.recipe.video.ui.RecipesVideoList
import com.feature.saved.recipes.ui.FavouriteRecipeScreen
import com.feature.settings.ui.SettingsView
import com.recipe.app.navigation.intent.*
import com.recipe.app.navigation.provider.MainViewNavigator

@Composable
fun BottomBar(items: List<BottomBarItems>) {

    val mainViewNavigator = MainViewNavigator.current
    val selectedIndex = rememberSaveable {
        mutableStateOf(0)
    }

    NavigationBar(
        modifier = Modifier.height(48.dp),
        containerColor = MaterialTheme.colorScheme.surface,
    ) {
        items.forEachIndexed { index, mainScreen ->
            val isSelected = selectedIndex.value == index
            NavigationBarItem(
                colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = MaterialTheme.colorScheme.onSecondaryContainer,
                        indicatorColor = MaterialTheme.colorScheme.surface ),
                alwaysShowLabel = false,
                icon = {
                    Icon(
                        mainScreen.getIcon(index),
                        contentDescription = mainScreen.tabName
                    )
                },
                selected = isSelected,
                onClick = {
                    selectedIndex.value = index
                    val route = mainScreen.routeName
                    val navController = mainViewNavigator.getNavController()
                    navController.navigate(route) {
                        navController.graph.startDestinationRoute?.let { route ->
                            popUpTo(route) {
                                saveState = true
                            }
                        }
                        restoreState = true
                        launchSingleTop = true
                    }
                }
            )
        }
    }
}

@ExperimentalFoundationApi
@ExperimentalAnimationApi
@Composable
fun NavigationView(
    searchViewModel: com.feature.recipe.list.viewmodel.SearchViewModel
) {

    val mainViewNavigator = MainViewNavigator.current
    AppNavHost(
        mainViewNavigator,
        startDestination = HomeViewIntent()
    ) {
        NavComposable(RecipeListIntent()) {
            val arguments = RecipeListIntent.getArguments(it.arguments)
            arguments?.let {
                RecipeListScreen(cuisineKey = arguments.recipeId)
            }
        }
        NavComposable(HomeViewIntent()) {
            HomeView()
        }
        NavComposable(RecipeVideoListIntent()) {
            RecipesVideoList()
        }
        NavComposable(SavedRecipeIntent()) {
            FavouriteRecipeScreen()
        }
        NavComposable(SearchViewIntent()) {
            SearchView(searchViewModel)
        }
        NavComposable(SettingsViewIntent()) {
            SettingsView()
        }
    }
}

data class BottomBarItems(val routeName: String, val tabName: String)

fun BottomBarItems.getIcon(index: Int): ImageVector =
    if (index == 0) {
        Icons.Default.Home
    } else if (index == 1) {
        Icons.Default.Favorite
    } else if (index == 2) {
        Icons.Default.PlayArrow
    } else {
        Icons.Default.Settings
    }
