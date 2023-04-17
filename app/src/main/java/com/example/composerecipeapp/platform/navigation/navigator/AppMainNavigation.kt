package com.example.composerecipeapp.platform.navigation.navigator

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

class AppMainNavigation @AssistedInject constructor(@Assisted val navHostController: NavHostController) :
    Navigator<DestinationIntent> {
    override fun navigateTo(
        navItems: DestinationIntent,
        popUpTo: DestinationIntent?,
        included: Boolean
    ) {
        try {
            val route = navItems.getRouteName()
            navHostController.navigate(route){
                popUpTo?.let {
                    popUpTo(it.getRouteName()) {
                        inclusive = included
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    @Composable
    override fun getCurrentRoute(): String? {
        val navBackStackEntry by navHostController.currentBackStackEntryAsState()
        return navBackStackEntry?.destination?.route
    }

    override fun getNavController(): NavController {
        return navHostController
    }

    fun popBackStack(){
        navHostController.popBackStack()
    }
}
