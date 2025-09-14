package com.khaki.smartrss.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance

// Define base light and dark color schemes using olive green as the accent
private val LightColors = lightColorScheme(
    primary = OliveGreen,
    onPrimary = ColorUtils.onPrimaryFor(OliveGreen),
    primaryContainer = OliveGreenLight,
    onPrimaryContainer = ColorUtils.onPrimaryFor(OliveGreenLight),

    secondary = Sage,
    onSecondary = ColorUtils.onPrimaryFor(Sage),
    secondaryContainer = SageLight,
    onSecondaryContainer = ColorUtils.onPrimaryFor(SageLight),

    tertiary = Amber,
    onTertiary = ColorUtils.onPrimaryFor(Amber),
    tertiaryContainer = AmberLight,
    onTertiaryContainer = ColorUtils.onPrimaryFor(AmberLight),

    background = SurfaceLight,
    onBackground = ColorUtils.onSurfaceFor(SurfaceLight),
    surface = SurfaceLight,
    onSurface = ColorUtils.onSurfaceFor(SurfaceLight),
    error = ErrorRed,
)

private val DarkColors = darkColorScheme(
    primary = OliveGreenLight,
    onPrimary = ColorUtils.onPrimaryFor(OliveGreenLight, dark = true),
    primaryContainer = OliveGreenDark,
    onPrimaryContainer = ColorUtils.onPrimaryFor(OliveGreenDark, dark = true),

    secondary = SageLight,
    onSecondary = ColorUtils.onPrimaryFor(SageLight, dark = true),
    secondaryContainer = SageDark,
    onSecondaryContainer = ColorUtils.onPrimaryFor(SageDark, dark = true),

    tertiary = AmberLight,
    onTertiary = ColorUtils.onPrimaryFor(AmberLight, dark = true),
    tertiaryContainer = AmberDark,
    onTertiaryContainer = ColorUtils.onPrimaryFor(AmberDark, dark = true),

    background = SurfaceDark,
    onBackground = ColorUtils.onSurfaceFor(SurfaceDark, dark = true),
    surface = SurfaceDark,
    onSurface = ColorUtils.onSurfaceFor(SurfaceDark, dark = true),
    error = ErrorRed,
)

// Expect/actual: Android will provide Material You (dynamic color) when available; other platforms return null
@Composable
expect fun dynamicMaterialYouColorScheme(darkTheme: Boolean): ColorScheme?

@Composable
fun SmartRssTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    useDynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val dynamic = if (useDynamicColor) dynamicMaterialYouColorScheme(darkTheme) else null
    val colors = dynamic ?: if (darkTheme) DarkColors else LightColors

    MaterialTheme(
        colorScheme = colors,
        shapes = Shapes,
        content = content
    )
}

// Small utility for contrasting on-colors; avoids pulling in extra libs
private object ColorUtils {
    fun onPrimaryFor(color: Color, dark: Boolean = false): Color {
        // Choose black or white based on luminance; tweak for dark scheme if needed
        val threshold = 0.5f
        val base = if (color.luminance() > threshold) Color(0xFF000000) else Color(0xFFFFFFFF)
        return if (dark) base.copy(alpha = 0.92f) else base
    }

    fun onSurfaceFor(color: Color, dark: Boolean = false): Color {
        val base = if (dark) Color(0xFFECECEC) else Color(0xFF1A1A1A)
        return base
    }
}