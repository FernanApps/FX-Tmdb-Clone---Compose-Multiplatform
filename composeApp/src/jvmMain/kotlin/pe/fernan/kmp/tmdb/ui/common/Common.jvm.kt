package pe.fernan.kmp.tmdb.ui.common

import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.LocalWindowInfo
import pe.fernan.kmp.tmdb.ui.ext.pxToDp

@OptIn(ExperimentalComposeUiApi::class)
@Composable
internal actual fun LocalCurrentSize() = CurrentWindowsSize(
    height = LocalWindowInfo.current.containerSize.height.pxToDp(),
    width = LocalWindowInfo.current.containerSize.width.pxToDp()
)

internal actual val CurrentPlatformTarget = PlatformTarget.Jvm
