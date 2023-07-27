package com.example.composerecipeapp.ui.main_view

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.NavigationRailItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.core.navigtion.navigator.AppNavHost
import com.core.navigtion.navigator.NavComposable
import com.feature.home.ui.HomeScreen
import com.feature.recipe.list.ui.RecipeListScreen
import com.feature.recipe.list.ui.SearchView
import com.feature.recipe.video.ui.RecipesVideoList
import com.feature.saved.recipes.ui.FavouriteRecipeScreen
import com.feature.settings.ui.SettingsView
import com.recipe.app.navigation.intent.HomeViewIntent
import com.recipe.app.navigation.intent.RecipeListIntent
import com.recipe.app.navigation.intent.RecipeVideoListIntent
import com.recipe.app.navigation.intent.SavedRecipeIntent
import com.recipe.app.navigation.intent.SearchViewIntent
import com.recipe.app.navigation.intent.SettingsViewIntent
import com.recipe.app.navigation.provider.MainViewNavigator

@Composable
fun BottomBar(items: List<BottomBarItems>) {

    val mainViewNavigator = MainViewNavigator.current
    val selectedIndex = rememberSaveable {
        mutableStateOf(0)
    }

    NavigationBar(
        modifier = Modifier
            .height(48.dp)
            .fillMaxWidth(),
        containerColor = MaterialTheme.colorScheme.surface,
    ) {
        items.forEachIndexed { index, mainScreen ->
            val isSelected = selectedIndex.value == index
            NavigationBarItem(
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.onSecondaryContainer,
                    indicatorColor = MaterialTheme.colorScheme.surface
                ),
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
                    routeTo(route, navController)
                }
            )
        }
    }
}


@Composable
fun NavRail(items: List<BottomBarItems>) {
    val mainViewNavigator = MainViewNavigator.current
    val selectedIndex = rememberSaveable {
        mutableStateOf(0)
    }

    NavigationRail(
        modifier = Modifier.fillMaxHeight(),
        containerColor = MaterialTheme.colorScheme.surface,
        contentColor = MaterialTheme.colorScheme.surface,
    ) {
        items.forEachIndexed { index, bottomBarItems ->
            val isSelected = selectedIndex.value == index
            NavigationRailItem(
                colors = NavigationRailItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.onSecondaryContainer,
                    indicatorColor = MaterialTheme.colorScheme.surface
                ),
                selected = isSelected,
                icon =  {
                    Icon(
                        bottomBarItems.getIcon(index),
                        contentDescription = bottomBarItems.tabName
                    )
                },
                onClick = {
                    selectedIndex.value = index
                    val route = bottomBarItems.routeName
                    val navController = mainViewNavigator.getNavController()
                    routeTo(route, navController)
                }
            )
        }
    }
}

fun routeTo(route: String, navController: NavController) {
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
            HomeScreen()
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
