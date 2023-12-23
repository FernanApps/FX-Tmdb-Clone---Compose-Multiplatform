package pe.fernan.kmp.tmdb.ui.common

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.AwaitPointerEventScope
import androidx.compose.ui.input.pointer.PointerEvent
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.unit.Dp

data class CurrentWindowsSize(val height: Dp, val width: Dp)

enum class PlatformTarget {
    Android, Jvm, Web
}

internal expect val CurrentPlatformTarget: PlatformTarget

@Composable
internal expect fun LocalCurrentSize(): CurrentWindowsSize

/*
Fix components/CardHorizontalPoster.kt:24:42 Unresolved reference: onPointerEvent
Fix components/CardHorizontalPoster.kt:60:23 Unresolved reference: onPointerEvent
Fix components/CardHorizontalPoster.kt:60:63 Cannot infer a type for this parameter. Please specify it explicitly.
Fix home/HomeScreen.kt:6:36 Unresolved reference: VerticalScrollbar
Fix home/HomeScreen.kt:32:36 Unresolved reference: rememberScrollbarAdapter
Fix home/HomeScreen.kt:490:21 Unresolved reference: VerticalScrollbar
Fix home/HomeScreen.kt:492:35 Unresolved reference: rememberScrollbarAdapter
Fix home/HomeSections.kt:2:36 Unresolved reference: HorizontalScrollbar
Fix home/HomeSections.kt:5:36 Unresolved reference: defaultScrollbarStyle
Fix home/HomeSections.kt:19:36 Unresolved reference: rememberScrollbarAdapter
Fix home/HomeSections.kt:242:17 Unresolved reference: HorizontalScrollbar
Fix home/HomeSections.kt:245:31 Unresolved reference: rememberScrollbarAdapter
Fix home/HomeSections.kt:246:29 Unresolved reference: defaultScrollbarStyle
Fix home/HomeSections.kt:248:29 Unresolved reference: it
Fix home/HomeSections.kt:253:29 Unresolved reference: it
 */



@OptIn(ExperimentalComposeUiApi::class)

@Composable
internal expect fun Modifier.onPointerEventCommon(
    eventType: PointerEventType,
    pass: PointerEventPass = PointerEventPass.Main,
    onEvent: AwaitPointerEventScope.(event: PointerEvent) -> Unit
): Modifier


@Composable
internal expect fun VerticalScrollbarCommon(
    adapter: Any,
    modifier: Modifier = Modifier,
    reverseLayout: Boolean = false,
    style: Any = Any(),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
)




@Composable
internal expect fun rememberScrollbarAdapterCommon(
    scrollState: LazyListState,
): Any




@Composable
internal expect fun HorizontalScrollbarCommon(
    adapter: Any,
    modifier: Modifier = Modifier,
    reverseLayout: Boolean = false,
    style: Any = Any(),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
)




internal expect fun defaultScrollbarStyleCommon(background: String?): Any
