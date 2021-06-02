package com.example.composerecipeapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigate
import androidx.navigation.compose.rememberNavController
import com.example.composerecipeapp.core.logger.enableLogging
import com.example.composerecipeapp.ui.ComposeRecipeAppTheme
import com.example.composerecipeapp.ui.recipes.*

class MainActivity : AppCompatActivity() {
    @ExperimentalComposeUiApi
    @ExperimentalAnimationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        WindowCompat.setDecorFitsSystemWindows(window, false)
        enableLogging = true
        setContent {
            ComposeRecipeAppTheme {
                MainApp()
            }
        }
    }
}

@ExperimentalComposeUiApi
@ExperimentalAnimationApi
@Composable
fun MainApp() {
    val navController = rememberNavController()
    CompositionLocalProvider(ParentNavHostController provides navController) {
        NavHost(navController = navController, startDestination = "main_recipes") {

            composable("main_recipes") {
                AppContent()
            }
            composable("recipe_details/{recipe_id}") {
                val id = it.arguments?.getString("recipe_id")
                id?.let {
                    RecipeDetail(id)
                }
            }
            composable("recipe/videos/{youtube_id}"){
                val id = it.arguments?.getString("youtube_id")
                it?.let {
                    VideoPlayer()
                }
            }
        }
    }

}

@ExperimentalComposeUiApi
@ExperimentalAnimationApi
@Composable
fun AppContent() {
    val navController = rememberNavController()
    val searchViewModel: SearchViewModel = viewModel()
    val scaffoldState = rememberScaffoldState()
    Scaffold(
        scaffoldState = scaffoldState,
        bottomBar = {
            BottomBar(
                navController = navController, items = listOf(
                    BottomBarItems("recipes/chicken", "Recipes"),
                    BottomBarItems("videos", "Videos"),
                )
            )
        },
        topBar = { SearchBar(navController, searchViewModel = searchViewModel) }
    ) {
        navigationConfigurations(
            navController = navController,
            searchViewModel
        )
    }
}

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

@ExperimentalComposeUiApi
@ExperimentalAnimationApi
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DefaultPreview() {
    ComposeRecipeAppTheme(darkTheme = false) {
        AppContent()
    }
}

@ExperimentalAnimationApi
@Composable
fun navigationConfigurations(
    navController: NavHostController,
    searchViewModel: SearchViewModel
) {
    NavHost(navController, startDestination = "recipes/{keyword}") {
        composable("recipes/{keyword}") {
            val keyword = it.arguments?.getString("keyword")
            RecipeView( key = keyword)
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

@SuppressLint("CompositionLocalNaming")
val ParentNavHostController = compositionLocalOf<NavHostController> {
    error("No navigation controller found")
}
