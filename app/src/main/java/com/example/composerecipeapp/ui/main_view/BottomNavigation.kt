package com.example.composerecipeapp.ui.main_view

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.composerecipeapp.platform.navigation.navigator.AppMainNavigation
import com.example.composerecipeapp.platform.navigation.navigator.AppNavHost
import com.example.composerecipeapp.platform.navigation.navigator.NavComposable
import com.example.composerecipeapp.platform.navigation.screens.*
import com.example.composerecipeapp.ui.home_view.HomeView
import com.example.composerecipeapp.ui.recipe_list.RecipeView
import com.example.composerecipeapp.ui.recipe_search.SearchView
import com.example.composerecipeapp.ui.recipe_videos.RecipesVideoList
import com.example.composerecipeapp.ui.saved_recipe.SaveRecipeView
import com.example.composerecipeapp.ui.settings.SettingsView
import com.example.composerecipeapp.viewmodel.recipe_search.SearchViewModel

@Composable
fun BottomBar(appMainNavigation: AppMainNavigation, items: List<BottomBarItems>) {

    val selectedIndex = rememberSaveable {
        mutableStateOf(0)
    }

    BottomNavigation {
        items.forEachIndexed { index, mainScreen ->
            BottomNavigationItem(
                icon = {
                    val isSelected = selectedIndex.value == index
                    Icon(
                        mainScreen.getIcon(index),
                        tint = if (isSelected) MaterialTheme.colors.secondary
                        else Color.LightGray,
                        contentDescription = mainScreen.tabName
                    )
                },
                selected = true,
                onClick = {
                    selectedIndex.value = index
                    val route = mainScreen.routeName
                    val navController = appMainNavigation.getNavController()
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
@ExperimentalMaterialApi
@ExperimentalAnimationApi
@Composable
fun NavigationView(
    navController: AppMainNavigation,
    searchViewModel: SearchViewModel,
    appMainNavigation: AppMainNavigation
) {
    AppNavHost(
        appMainNavigation,
        startDestination = HomeViewIntent()
    ) {
        NavComposable(RecipeListIntent()) {
            val arguments = RecipeListIntent.getArguments(it.arguments)
            arguments?.let {
                RecipeView(cuisineKey = arguments.recipeId, navController)
            }
        }
        NavComposable(HomeViewIntent()) {
            HomeView(navController, appMainNavigation)
        }
        NavComposable(RecipeVideoListIntent()) {
            RecipesVideoList(navController)
        }
        NavComposable(SavedRecipeIntent()) {
            SaveRecipeView(appMainNavigation = appMainNavigation)
        }
        NavComposable(SearchViewIntent()) {
            SearchView(appMainNavigation, searchViewModel)
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
