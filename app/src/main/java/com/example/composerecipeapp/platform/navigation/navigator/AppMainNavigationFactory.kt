package com.example.composerecipeapp.platform.navigation.navigator

import androidx.navigation.NavHostController
import dagger.assisted.AssistedFactory

@AssistedFactory
interface AppMainNavigationFactory {

    fun create(navHostController: NavHostController) : AppMainNavigation
}
