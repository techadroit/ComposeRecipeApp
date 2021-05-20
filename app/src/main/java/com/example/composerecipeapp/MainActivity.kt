package com.example.composerecipeapp

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigate
import androidx.navigation.compose.rememberNavController
import com.example.composerecipeapp.ui.ComposeRecipeAppTheme
import com.example.composerecipeapp.ui.recipes.RecipeDetail
import com.example.composerecipeapp.ui.recipes.RecipeView
import com.example.composerecipeapp.ui.recipes.RecipesVideoList
import com.example.composerecipeapp.ui.recipes.SearchView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeRecipeAppTheme {
                mainApp()
            }
        }
    }
}

@Composable
fun mainApp() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "main_recipes") {
        composable("main_recipes") {
            appContent(navController)
        }
        composable("recipe_details/{recipe_id}") {
            val id = it.arguments?.getString("recipe_id")
            id?.let {
                RecipeDetail(id)
            }
        }
    }
}

@Composable
fun appContent(parentNavHostController: NavHostController) {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = {
            bottomBar(
                navController = navController, items = listOf(
                    MainScreen("recipes", "Recipes"),
                    MainScreen("videos", "Videos"),
                )
            )
        },
        topBar = { SearchView() }
    ) {
        navigationConfigurations(navController = navController, parentNavHostController)
    }
}

@Composable
fun bottomBar(navController: NavHostController, items: List<MainScreen>) {
    BottomNavigation {
        items.forEachIndexed { index, mainScreen ->
            BottomNavigationItem(
                icon = { Text(text = mainScreen.tabName) },
                selected = true,
                onClick = {
                    val route = mainScreen.routeName
                    navController.navigate(route)
                })
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DefaultPreview() {
    ComposeRecipeAppTheme(darkTheme = false) {
        appContent(rememberNavController())
    }
}

@Composable
fun navigationConfigurations(
    navController: NavHostController,
    parentNavHostController: NavHostController
) {
    NavHost(navController, startDestination = "recipes") {
        composable("recipes") {
            RecipeView(parentNavHostController)
        }
        composable("videos") {
            RecipesVideoList()
        }
    }
}

class MainScreen(val routeName: String, val tabName: String) {
    @Composable
    fun body(message: String) {
        Surface(modifier = Modifier.fillMaxSize()) {
            Text(text = message)
        }
    }
}
