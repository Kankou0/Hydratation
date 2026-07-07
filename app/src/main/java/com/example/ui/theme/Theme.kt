package com.example.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme =
  darkColorScheme(
    primary = TurquoisePrimary,
    onPrimary = TurquoiseOnPrimary,
    primaryContainer = TurquoisePrimaryContainer,
    onPrimaryContainer = TurquoiseOnPrimaryContainer,
    secondary = TurquoiseSecondary,
    onSecondary = TurquoiseOnSecondary,
    secondaryContainer = TurquoiseSecondaryContainer,
    onSecondaryContainer = TurquoiseOnSecondaryContainer,
    tertiary = TurquoiseTertiary,
    onTertiary = TurquoiseOnTertiary,
    background = TurquoiseBackground,
    onBackground = TurquoiseOnBackground,
    surface = TurquoiseSurface,
    onSurface = TurquoiseOnSurface,
    surfaceVariant = TurquoiseSurfaceVariant,
    onSurfaceVariant = TurquoiseOnSurfaceVariant,
    outline = TurquoiseOutline
  )

private val LightColorScheme = DarkColorScheme // Standardize on Dark Turquoise since Dark Theme was requested

@Composable
fun MyApplicationTheme(
  darkTheme: Boolean = true, // Force Dark Theme as requested
  dynamicColor: Boolean = false, // Disable dynamic colors to preserve turquoise accents
  content: @Composable () -> Unit,
) {
  val colorScheme = if (darkTheme) DarkColorScheme else DarkColorScheme // Force dark theme


  MaterialTheme(colorScheme = colorScheme, typography = Typography, content = content)
}
