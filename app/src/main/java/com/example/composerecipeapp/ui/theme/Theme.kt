package com.example.composerecipeapp.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import com.example.composerecipeapp.ui.shapes

private val DarkColorPalette = darkColors(
    primary = primaryColorDark,
    primaryVariant = primaryVariantColorDark,
    onPrimary = onPrimary,
    secondary = secondaryColorDark,
    background = backgroundColorDark,
    surface = surfaceColorDark,
    onBackground = onBackgroundColorDark,
    onSurface = onSurfaceColorDark,
    error = errorColor
)

private val LightColorPalette = lightColors(
    primary = primaryColorLight,
    primaryVariant = primaryVariantColorLight,
    secondary = secondaryColorLight,
    onPrimary = onPrimaryLight,
    background = backgroundColorLight,
    surface = surfaceColorLight,
    onBackground = onBackgroundColorLight,
    onSurface = onSurfaceColorLight,
    error = errorColor
)

private val UserInterestColorPalette = lightColors(
    primary= surfaceColor,
    onPrimary = onPrimaryLight,
    primaryVariant = surfaceColor,
    surface = surfaceColor,
    secondary = secondaryColor,
    background = backgroundColor,
    onBackground = onBackgroundColorLight,
    onSurface = onSurfaceColorLight
)

@Composable
fun ComposeRecipeAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    AppTheme(darkTheme = darkTheme, colors = colors, content = content)
}

@Composable
fun UserInterestComposable(
    darkTheme: Boolean,
    content: @Composable () -> Unit
) {
        AppTheme(darkTheme = darkTheme, colors = UserInterestColorPalette,content = content)
}

@Composable
fun AppTheme(
    darkTheme: Boolean,
    colors: Colors,
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colors = colors,
        typography = Type.create(darkTheme),
        shapes = shapes,
        content = content
    )
}
