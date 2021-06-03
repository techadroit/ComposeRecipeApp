package com.example.composerecipeapp.ui

import androidx.navigation.compose.NamedNavArgument

interface NavigationCommand{
    val arguments: List<NamedNavArgument>
    val destination: String
}
