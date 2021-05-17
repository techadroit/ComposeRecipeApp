package com.example.composerecipeapp.ui

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColors(
    primary = primaryColorDark,
    primaryVariant = primaryColorDark,
    onPrimary = Color.White,
    secondary = secondaryColorDark,
    background = backgroundColorDark,
    surface = surfaceColorDark,
    onBackground = onBackgroundColorDark,
    onSurface = onSurfaceColorDark,
    error = errorColor
)

private val LightColorPalette = lightColors(
    primary = primaryColorLight,
    primaryVariant = primaryColorLight,
    secondary = secondaryColorLight,
    onPrimary = Color.Black,
    background = backgroundColorLight,
    surface = surfaceColorLight,
    onBackground = onBackgroundColorLight,
    onSurface = onSurfaceColorLight,
    error = errorColor

    /* Other default colors to override
background = Color.White,
surface = Color.White,
onPrimary = Color.White,
onSecondary = Color.Black,
onBackground = Color.Black,
onSurface = Color.Black,
*/
)

@Composable
fun ComposeRecipeAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable() () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = typography,
        shapes = shapes,
        content = content
    )
}