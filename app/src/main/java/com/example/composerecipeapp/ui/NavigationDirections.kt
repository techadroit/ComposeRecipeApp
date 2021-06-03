package com.example.composerecipeapp.ui

import androidx.navigation.compose.NamedNavArgument

object NavigationDirections {

    val recipeList = object : NavigationCommand{
        override val arguments: List<NamedNavArgument>
            get() = emptyList()
        override val destination: String
            get() = "recipes/{keyword}"
    }

    val videoPlayer = object : NavigationCommand{
        override val arguments: List<NamedNavArgument>
            get() = emptyList()
        override val destination: String
            get() = "recipe/videos/{youtube_id}"
    }

    val recipeVideoDestination = object : NavigationCommand{
        override val arguments: List<NamedNavArgument>
            get() = emptyList()
        override val destination: String
            get() = "videos"
    }

    val recipeDetailDestination = object : NavigationCommand{
        override val arguments: List<NamedNavArgument>
            get() = emptyList()
        override val destination: String
            get() = "recipe_details/{recipe_id}"
    }

    val searchDestination = object : NavigationCommand{
        override val arguments: List<NamedNavArgument>
            get() = emptyList()
        override val destination: String
            get() = "search"
    }

    val mainDestination = object : NavigationCommand{
        override val arguments: List<NamedNavArgument>
            get() = emptyList()
        override val destination: String
            get() = "main_recipe"
    }

}
