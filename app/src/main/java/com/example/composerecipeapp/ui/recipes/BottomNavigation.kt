package com.example.composerecipeapp.ui.recipes

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.composerecipeapp.ui.recipe_list.RecipeView
import com.example.composerecipeapp.ui.recipe_search.SearchView
import com.example.composerecipeapp.ui.recipe_search.SearchViewModel
import com.example.composerecipeapp.ui.recipe_videos.RecipesVideoList

@Composable
fun BottomBar(navController: NavHostController, items: List<BottomBarItems>) {

    val selectedIndex = remember {
        mutableStateOf(0)
    }

    BottomNavigation {
        items.forEachIndexed { index, mainScreen ->
            BottomNavigationItem(
                icon = {
                    val isSelected = selectedIndex.value == index
                    Icon(
                        mainScreen.getIcon(),
                        tint = if (isSelected) MaterialTheme.colors.secondary
                        else Color.LightGray,
                        contentDescription = mainScreen.tabName
                    )
                },
                selected = true,
                onClick = {
                    selectedIndex.value = index
                    val route = mainScreen.routeName
                    navController.navigate(route)
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
    NavHost(navController, startDestination = "recipes/{keyword}") {
        composable("recipes/{keyword}") {
            val keyword = it.arguments?.getString("keyword")
            RecipeView(key = keyword)
        }
        composable("videos") {
            RecipesVideoList()
        }
        composable("search") {
            SearchView(navController, searchViewModel)
        }
    }
}

data class BottomBarItems(val routeName: String, val tabName: String)

fun BottomBarItems.getIcon(): ImageVector =
    if (routeName.startsWith("recipes/")) {
        Icons.Default.Home
    } else {
        Icons.Default.PlayArrow
    }
