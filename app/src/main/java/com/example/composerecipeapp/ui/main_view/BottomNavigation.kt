package com.example.composerecipeapp.ui.main_view

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.composerecipeapp.ui.navigation.NavigationDirections
import com.example.composerecipeapp.ui.recipe_list.RecipeView
import com.example.composerecipeapp.ui.recipe_search.SearchView
import com.example.composerecipeapp.viewmodel.recipe_search.SearchViewModel
import com.example.composerecipeapp.ui.recipe_videos.RecipesVideoList
import com.example.composerecipeapp.ui.saved_recipe.SaveRecipeView

@Composable
fun BottomBar(navController: NavHostController, items: List<BottomBarItems>) {

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val selectedIndex = remember {
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

@ExperimentalAnimationApi
@Composable
fun NavigationView(
    navController: NavHostController,
    searchViewModel: SearchViewModel
) {
    NavHost(navController, startDestination = NavigationDirections.recipeList.destination) {
        composable(NavigationDirections.recipeList.destination) {
            val keyword = it.arguments?.getString("keyword")
            RecipeView(key = keyword)
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
    }
}

data class BottomBarItems(val routeName: String, val tabName: String)

fun BottomBarItems.getIcon(index: Int): ImageVector =
    if (index == 0) {
        Icons.Default.Home
    } else if(index == 1){
        Icons.Default.Favorite
    }else {
        Icons.Default.PlayArrow
    }
