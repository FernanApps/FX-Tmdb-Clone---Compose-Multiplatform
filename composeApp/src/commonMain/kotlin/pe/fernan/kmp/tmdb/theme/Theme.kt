package pe.fernan.kmp.tmdb.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Surface
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val LightColorScheme = lightColorScheme(
    primary = md_theme_light_primary,
    onPrimary = md_theme_light_onPrimary,
    primaryContainer = md_theme_light_primaryContainer,
    onPrimaryContainer = md_theme_light_onPrimaryContainer,
    secondary = md_theme_light_secondary,
    onSecondary = md_theme_light_onSecondary,
    secondaryContainer = md_theme_light_secondaryContainer,
    onSecondaryContainer = md_theme_light_onSecondaryContainer,
    tertiary = md_theme_light_tertiary,
    onTertiary = md_theme_light_onTertiary,
    tertiaryContainer = md_theme_light_tertiaryContainer,
    onTertiaryContainer = md_theme_light_onTertiaryContainer,
    error = md_theme_light_error,
    errorContainer = md_theme_light_errorContainer,
    onError = md_theme_light_onError,
    onErrorContainer = md_theme_light_onErrorContainer,
    background = md_theme_light_background,
    onBackground = md_theme_light_onBackground,
    surface = md_theme_light_surface,
    onSurface = md_theme_light_onSurface,
    surfaceVariant = md_theme_light_surfaceVariant,
    onSurfaceVariant = md_theme_light_onSurfaceVariant,
    outline = md_theme_light_outline,
    inverseOnSurface = md_theme_light_inverseOnSurface,
    inverseSurface = md_theme_light_inverseSurface,
    inversePrimary = md_theme_light_inversePrimary,
    surfaceTint = md_theme_light_surfaceTint,
    outlineVariant = md_theme_light_outlineVariant,
    scrim = md_theme_light_scrim,
)

private val DarkColorScheme = darkColorScheme(
    primary = md_theme_dark_primary,
    onPrimary = md_theme_dark_onPrimary,
    primaryContainer = md_theme_dark_primaryContainer,
    onPrimaryContainer = md_theme_dark_onPrimaryContainer,
    secondary = md_theme_dark_secondary,
    onSecondary = md_theme_dark_onSecondary,
    secondaryContainer = md_theme_dark_secondaryContainer,
    onSecondaryContainer = md_theme_dark_onSecondaryContainer,
    tertiary = md_theme_dark_tertiary,
    onTertiary = md_theme_dark_onTertiary,
    tertiaryContainer = md_theme_dark_tertiaryContainer,
    onTertiaryContainer = md_theme_dark_onTertiaryContainer,
    error = md_theme_dark_error,
    errorContainer = md_theme_dark_errorContainer,
    onError = md_theme_dark_onError,
    onErrorContainer = md_theme_dark_onErrorContainer,
    background = md_theme_dark_background,
    onBackground = md_theme_dark_onBackground,
    surface = md_theme_dark_surface,
    onSurface = md_theme_dark_onSurface,
    surfaceVariant = md_theme_dark_surfaceVariant,
    onSurfaceVariant = md_theme_dark_onSurfaceVariant,
    outline = md_theme_dark_outline,
    inverseOnSurface = md_theme_dark_inverseOnSurface,
    inverseSurface = md_theme_dark_inverseSurface,
    inversePrimary = md_theme_dark_inversePrimary,
    surfaceTint = md_theme_dark_surfaceTint,
    outlineVariant = md_theme_dark_outlineVariant,
    scrim = md_theme_dark_scrim,
)

private val AppShapes = Shapes(
    extraSmall = RoundedCornerShape(2.dp),
    small = RoundedCornerShape(4.dp),
    medium = RoundedCornerShape(8.dp),
    large = RoundedCornerShape(16.dp),
    extraLarge = RoundedCornerShape(32.dp)
)

// Declare the font families
object AppFont {
    var CustomBaseFont: FontFamily? = null
    val BaseFont
        get() = CustomBaseFont ?: FontFamily.SansSerif
}

private val AppTypography = Typography(
    bodyMedium = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp
    )
)

val FinalAppTypography = Typography(
    displayLarge = AppTypography.displayLarge.copy(fontFamily = AppFont.BaseFont),
    displayMedium = AppTypography.displayMedium.copy(fontFamily = AppFont.BaseFont),
    displaySmall = AppTypography.displaySmall.copy(fontFamily = AppFont.BaseFont),

    headlineLarge = AppTypography.headlineLarge.copy(fontFamily = AppFont.BaseFont),
    headlineMedium = AppTypography.headlineMedium.copy(fontFamily = AppFont.BaseFont),
    headlineSmall = AppTypography.headlineSmall.copy(fontFamily = AppFont.BaseFont),

    titleLarge = AppTypography.titleLarge.copy(fontFamily = AppFont.BaseFont),
    titleMedium = AppTypography.titleMedium.copy(fontFamily = AppFont.BaseFont),
    titleSmall = AppTypography.titleSmall.copy(fontFamily = AppFont.BaseFont),

    bodyLarge = AppTypography.bodyLarge.copy(fontFamily = AppFont.BaseFont),
    bodyMedium = AppTypography.bodyMedium.copy(fontFamily = AppFont.BaseFont),
    bodySmall = AppTypography.bodySmall.copy(fontFamily = AppFont.BaseFont),

    labelLarge = AppTypography.labelLarge.copy(fontFamily = AppFont.BaseFont),
    labelMedium = AppTypography.labelMedium.copy(fontFamily = AppFont.BaseFont),
    labelSmall = AppTypography.labelSmall.copy(fontFamily = AppFont.BaseFont)
)

enum class WindowSize {
    Compact, Medium, Expanded
}

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
internal val LocalWindowSizeHeight = staticCompositionLocalOf<WindowSize> {
    error("CompositionLocal LocalWindowSizeClass not present")
}

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
internal val LocalWindowSizeWidth = staticCompositionLocalOf<WindowSize> {
    error("CompositionLocal LocalWindowSizeClass not present")
}

internal val LocalThemeIsDark = compositionLocalOf { mutableStateOf(true) }

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
internal fun AppTheme(
    content: @Composable() () -> Unit
) {
    val widthSize = when(calculateWindowSizeClass().widthSizeClass) {
        WindowWidthSizeClass.Expanded -> WindowSize.Expanded
        WindowWidthSizeClass.Medium -> WindowSize.Medium
        else -> WindowSize.Compact
    }
    val heightSize = when(calculateWindowSizeClass().heightSizeClass) {
        WindowHeightSizeClass.Expanded -> WindowSize.Expanded
        WindowHeightSizeClass.Medium -> WindowSize.Medium
        else -> WindowSize.Compact
    }

    val systemIsDark = isSystemInDarkTheme()
    val isDarkState = remember { mutableStateOf(systemIsDark) }
    CompositionLocalProvider(
        LocalThemeIsDark provides isDarkState,
        LocalWindowSizeWidth provides widthSize,
        LocalWindowSizeHeight provides heightSize
    ) {
        val isDark by isDarkState
        SystemAppearance(!isDark)
        MaterialTheme(
            colorScheme = /*if (isDark) DarkColorScheme else */LightColorScheme,
            typography = FinalAppTypography,
            shapes = AppShapes,
            content = {
                Surface(content = content)
            }
        )
    }
}

@Composable
internal expect fun SystemAppearance(isDark: Boolean)
