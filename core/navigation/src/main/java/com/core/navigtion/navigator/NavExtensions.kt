package com.core.navigtion.navigator

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.*
import androidx.navigation.compose.ComposeNavigator
import com.core.navigtion.intent.DestinationIntent

@Composable
public fun AppNavHost(
    navigator: Navigator<*>,
    startDestination: DestinationIntent,
    modifier: Modifier = Modifier,
    route: String? = null,
    builder: NavGraphBuilder.() -> Unit
) {
    val navController = navigator.getNavController()
    androidx.navigation.compose.NavHost(
        navController as NavHostController,
        remember(route, startDestination, builder) {
            navController.createGraph(startDestination.screenName, route, builder)
        },
        modifier
    )
}

public fun NavGraphBuilder.NavComposable(
    route: DestinationIntent,
    arguments: List<NamedNavArgument> = emptyList(),
    deepLinks: List<NavDeepLink> = emptyList(),
    content: @Composable AnimatedContentScope.(@JvmSuppressWildcards NavBackStackEntry) -> Unit
) {
    addDestination(
        ComposeNavigator.Destination(provider[ComposeNavigator::class], content).apply {
            this.route = route.screenName
            arguments.forEach { (argumentName, argument) ->
                addArgument(argumentName, argument)
            }
            deepLinks.forEach { deepLink ->
                addDeepLink(deepLink)
            }
        }
    )
}
