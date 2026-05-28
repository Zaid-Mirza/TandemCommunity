package com.tandem.community.ui.theme


import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val LightColorScheme = lightColorScheme(
    primary = Brand500,
    onPrimary = White,
    primaryContainer = Brand100,
    onPrimaryContainer = Brand500,

    secondary = Grey300,
    onSecondary = White,

    surface = White,
    onSurface = Grey900,
    surfaceVariant = Grey100,
    onSurfaceVariant = Grey600,

    outline = Grey200,
    outlineVariant = Grey500,

    background = Grey50,
    onBackground = Grey900,
)

private val DarkColorScheme = darkColorScheme(
    primary = Brand500,
    onPrimary = White,
    primaryContainer = Brand100,
    onPrimaryContainer = Brand500,

    secondary = Dark700,
    onSecondary = Grey500,

    surface = Dark800,
    onSurface = White,
    surfaceVariant = Dark700,
    onSurfaceVariant = Grey500,

    outline = Dark600,
    outlineVariant = Grey500,

    background = Color(0xFF121212),
    onBackground = White,
)

@Composable
fun TandemCommunityTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true, content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme, typography = Typography, content = content
    )
}