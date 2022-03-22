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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.composerecipeapp.ui.home_view.HomeView
import com.example.composerecipeapp.ui.navigation.NavigationDirections
import com.example.composerecipeapp.ui.recipe_list.RecipeView
import com.example.composerecipeapp.ui.recipe_search.SearchView
import com.example.composerecipeapp.ui.recipe_videos.RecipesVideoList
import com.example.composerecipeapp.ui.saved_recipe.SaveRecipeView
import com.example.composerecipeapp.ui.settings.SettingsView
import com.example.composerecipeapp.viewmodel.main.MainViewModel
import com.example.composerecipeapp.viewmodel.recipe_search.SearchViewModel

@ExperimentalMaterialApi
@Composable
fun BottomBar(
    navController: NavHostController,
    items: List<BottomBarItems>,
    mainViewModel: MainViewModel = hiltViewModel()
) {

    val selectedIndex = rememberSaveable {
        mutableStateOf(0)
    }

    val counter = mainViewModel.counter.collectAsState().value
    val showBadge = mainViewModel.showBadge.collectAsState().value

    BottomNavigation {

        items.forEachIndexed { index, mainScreen ->
            BottomNavigationItem(
                icon = {
                    val isSelected = selectedIndex.value == index
                    if (index == 1 && showBadge) {
                        BadgeBox(badgeContent = {
                            if (counter > 0) {
                                Text(text = counter.toString())
                            }
                        }) {
                            BottomIcons(
                                index = index,
                                bottomBarItems = mainScreen,
                                isSelected = isSelected
                            )
                        }
                    } else {
                        BottomIcons(
                            index = index,
                            bottomBarItems = mainScreen,
                            isSelected = isSelected
                        )
                    }
                },
                selected = true,
                onClick = {
                    mainViewModel.onScreenChange(index)
                    selectedIndex.value = index
                    val route = mainScreen.routeName
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

@Composable
fun BottomIcons(index: Int, bottomBarItems: BottomBarItems, isSelected: Boolean) {
    Icon(
        bottomBarItems.getIcon(index),
        tint = if (isSelected) MaterialTheme.colors.secondary
        else Color.LightGray,
        contentDescription = bottomBarItems.tabName
    )
}

@ExperimentalFoundationApi
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
            keyword?.let {
                RecipeView(cuisineKey = keyword)
            }
        }
        composable(NavigationDirections.homeView.destination) {
            HomeView(navController)
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
    } else if (index == 1) {
        Icons.Default.Favorite
    } else if (index == 2) {
        Icons.Default.PlayArrow
    } else {
        Icons.Default.Settings
    }

const val HOME = 0
const val FAVOURITES = 1
const val VIDEOS = 2
const val SETTINGS = 3
