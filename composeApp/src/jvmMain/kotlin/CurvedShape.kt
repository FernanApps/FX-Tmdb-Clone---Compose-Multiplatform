import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection

enum class CurveType {
    LTR,
    RTL
}
@Preview
@Composable
fun PreviewCurvedShape() {
    val modifier = Modifier.fillMaxSize()
    Box(
        modifier = modifier
            .graphicsLayer {
                shape = CurvedShape(CurveType.LTR)
                clip = true
            }
            .background(Color.Cyan)
    )
}




class CurvedShape(private val type: CurveType) : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        return Outline.Generic(
            path = if (type == CurveType.LTR) ltrCurve(size)
            else rtlCurve(size)
        )
    }
}

fun ltrCurve(size: Size) = Path().apply {
    reset()
    val width = size.width
    val height = size.height
    val radius = 100f
    val upShift = height * (1f - 0.2f)
    // arc C1
    arcTo(
        rect = Rect(
            left = 0f,
            top = 0f,
            right = radius * 2,
            bottom = radius * 2
        ),
        startAngleDegrees = 180f,
        sweepAngleDegrees = 90f,
        forceMoveTo = false
    )
    // line C1 to C2
    lineTo(width - radius, 0f)
    // arc C2
    arcTo(
        rect = Rect(
            left = width - radius * 2,
            top = 0f,
            right = width,
            bottom = radius * 2
        ),
        startAngleDegrees = 270f,
        sweepAngleDegrees = 90f,
        forceMoveTo = false
    )
    // line C2 to C3
    lineTo(width, height - (radius * 2))
    // arc C3
    arcTo(
        rect = Rect(
            left = width - radius * 2,
            top = height - (radius * 2),
            right = width,
            bottom = height
        ),
        startAngleDegrees = 0f,
        sweepAngleDegrees = 115f,
        forceMoveTo = false
    )
    // arc C4
    arcTo(
        rect = Rect(
            left = 0f,
            top = upShift - radius * 2,
            right = radius * 2,
            bottom = upShift
        ),
        startAngleDegrees = 115f,
        sweepAngleDegrees = 65f,
        forceMoveTo = false
    )
}


fun rtlCurve(size: Size) = Path().apply {
    reset()
    val width = size.width
    val height = size.height
    val radius = 100f
    val upShift = height * (1f - 0.5f)
    // arc C1
    arcTo(
        rect = Rect(
            left = 0f,
            top = 0f,
            right = radius * 2,
            bottom = radius * 2
        ),
        startAngleDegrees = 180f,
        sweepAngleDegrees = 110f,
        forceMoveTo = false
    )
    // arc C2
    arcTo(
        rect = Rect(
            left = width - radius * 2,
            top = upShift - 10,
            right = width,
            bottom = upShift + radius * 2
        ),
        startAngleDegrees = -60f,
        sweepAngleDegrees = 65f,
        forceMoveTo = false
    )
    // arc C3
    arcTo(
        rect = Rect(
            left = width - radius * 2,
            top = height - radius * 2,
            right = width,
            bottom = height
        ),
        startAngleDegrees = 0f,
        sweepAngleDegrees = 90f,
        forceMoveTo = false
    )
    // arc C4
    arcTo(
        rect = Rect(
            left = 0f,
            top = height - radius * 2,
            right = radius * 2,
            bottom = height
        ),
        startAngleDegrees = 90f,
        sweepAngleDegrees = 90f,
        forceMoveTo = false
    )
}