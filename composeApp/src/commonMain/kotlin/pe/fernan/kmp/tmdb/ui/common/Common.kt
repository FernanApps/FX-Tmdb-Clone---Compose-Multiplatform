package pe.fernan.kmp.tmdb.ui.common

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp

data class CurrentWindowsSize(val height: Dp, val width: Dp)

enum class PlatformTarget{
    Android, Jvm, Web
}

internal expect val CurrentPlatformTarget: PlatformTarget

@Composable
internal expect fun LocalCurrentSize(): CurrentWindowsSize