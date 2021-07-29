package com.example.composerecipeapp.ui.main_view

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.composerecipeapp.ui.home_view.HomeView
import com.example.composerecipeapp.ui.navigation.NavigationDirections
import com.example.composerecipeapp.ui.recipe_list.RecipeView
import com.example.composerecipeapp.ui.recipe_search.SearchView
import com.example.composerecipeapp.viewmodel.recipe_search.SearchViewModel
import com.example.composerecipeapp.ui.recipe_videos.RecipesVideoList
import com.example.composerecipeapp.ui.saved_recipe.SaveRecipeView
import com.example.composerecipeapp.ui.settings.SettingsView

@Composable
fun BottomBar(navController: NavHostController, items: List<BottomBarItems>) {

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
                    navController.navigate(route){
                        navController.graph.startDestinationRoute?.let { route ->
                            popUpTo(route) {
                                saveState = true
                            }
                        }
                        restoreState = true
                        launchSingleTop = true
                    }
                })
        }
    }
}

@ExperimentalMaterialApi
@ExperimentalAnimationApi
@Composable
fun NavigationView(
    navController: NavHostController,
    searchViewModel: SearchViewModel
) {
    NavHost(navController, startDestination = NavigationDirections.homeView.destination) {
        composable(NavigationDirections.recipeList.destination) {
            val keyword = it.arguments?.getString("keyword")
            RecipeView(key = keyword)
        }
        composable(NavigationDirections.homeView.destination){
            HomeView()
        }
        composable(NavigationDirections.recipeVideoDestination.destination) {
            RecipesVideoList()
        }
        composable(NavigationDirections.savedRecipeDestination.destination) {
            SaveRecipeView()
        }
        composable(NavigationDirections.searchDestination.destination) {
            SearchView(navController, searchViewModel)
        }
        composable(NavigationDirections.settings.destination) {
            SettingsView()
        }
    }
}

data class BottomBarItems(val routeName: String, val tabName: String)

fun BottomBarItems.getIcon(index: Int): ImageVector =
    if (index == 0) {
        Icons.Default.Home
    } else if(index == 1){
        Icons.Default.Favorite
    } else if(index == 2) {
        Icons.Default.PlayArrow
    }else{
        Icons.Default.Settings
    }
