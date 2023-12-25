package pe.fernan.kmp.tmdb.ui.common

import androidx.compose.foundation.HorizontalScrollbar
import androidx.compose.foundation.LocalScrollbarStyle
import androidx.compose.foundation.ScrollbarStyle
import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.defaultScrollbarStyle
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.AwaitPointerEventScope
import androidx.compose.ui.input.pointer.PointerEvent
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.onPointerEvent
import androidx.compose.ui.platform.LocalWindowInfo
import pe.fernan.kmp.tmdb.ui.ext.pxToDp

@OptIn(ExperimentalComposeUiApi::class)
@Composable
internal actual fun LocalCurrentSize() = CurrentWindowsSize(
    height = LocalWindowInfo.current.containerSize.height.pxToDp(),
    width = LocalWindowInfo.current.containerSize.width.pxToDp()
)

internal actual val CurrentPlatformTarget = PlatformTarget.Web

@OptIn(ExperimentalComposeUiApi::class)
@Composable
internal actual fun Modifier.onPointerEventCommon(
    eventType: PointerEventType,
    pass: PointerEventPass,
    onEvent: AwaitPointerEventScope.(event: PointerEvent) -> Unit
): Modifier = this.onPointerEvent(eventType, pass, onEvent)


@Composable
internal actual fun VerticalScrollbarCommon(
    adapter: Any,
    modifier: Modifier,
    reverseLayout: Boolean,
    style: Any?,
    interactionSource: MutableInteractionSource
) = VerticalScrollbar(
    adapter as androidx.compose.foundation.v2.ScrollbarAdapter,
    modifier, reverseLayout,
    if(style == null) LocalScrollbarStyle.current else style as ScrollbarStyle,
    interactionSource
)

@Composable
internal actual fun rememberScrollbarAdapterCommon(scrollState: LazyGridState): Any =
    rememberScrollbarAdapter(scrollState)

@Composable
internal actual fun rememberScrollbarAdapterCommon(scrollState: LazyListState): Any =
    rememberScrollbarAdapter(scrollState)

private fun defaultScrollbarStyleBase(background: String?): Any = defaultScrollbarStyle().let {
    if (background != null) {
        it.copy(
            unhoverColor = Color.Gray.copy(alpha = 0.35f),
            hoverColor = Color.Gray.copy(alpha = 0.60f)
        )
    } else {
        it
    }
}

internal actual fun defaultScrollbarStyleCommon(background: String?): Any =
    defaultScrollbarStyleBase(background)


@Composable
internal actual fun HorizontalScrollbarCommon(
    adapter: Any,
    modifier: Modifier,
    reverseLayout: Boolean,
    style: Any,
    interactionSource: MutableInteractionSource
) = HorizontalScrollbar(
    adapter as androidx.compose.foundation.v2.ScrollbarAdapter,
    modifier,
    reverseLayout,
    style as ScrollbarStyle,
    interactionSource
)

