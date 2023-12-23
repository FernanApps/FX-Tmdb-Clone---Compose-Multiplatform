package pe.fernan.kmp.tmdb.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.BaselineShift
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun CircularProgressbar1(
    size: Dp = 260.dp,
    foregroundIndicatorColor: Color = Color(0xFF35898f),
    backgroundColor: Color = Color.Blue,
    backgroundInternalColor: Color = Color.Blue,
    backgroundInternalStroke: Dp = 2.dp,
    shadowColor: Color = Color.White,
    indicatorThickness: Dp = 24.dp,
    initialValue: Float = 60f,
    animationDuration: Int = 1000,
    textStyle: TextStyle = MaterialTheme.typography.bodySmall
) {

    // It remembers the data usage value
    var dataUsageRemember by remember {
        mutableStateOf(-1f)
    }


    // This is to animate the foreground indicator
    val dataUsageAnimate = animateFloatAsState(
        targetValue = dataUsageRemember,
        animationSpec = tween(
            durationMillis = animationDuration
        )
    )

    // This is to start the animation when the activity is opened
    LaunchedEffect(Unit) {
        dataUsageRemember = initialValue
    }

    Box(
        modifier = Modifier.size(size),
        contentAlignment = Alignment.Center
    ) {
        Canvas(
            modifier = Modifier
                .size(size)
        ) {
            // For shadow

            drawCircle(
                color = backgroundColor,
                radius = (this.size.height / 2) + backgroundInternalStroke.toPx(),
                center = Offset(x = this.size.width / 2, y = this.size.height / 2)
            )


            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(shadowColor, Color.Transparent),
                    center = Offset(x = this.size.width / 2, y = this.size.height / 2),
                    radius = this.size.height / 2
                ),
                radius = (this.size.height / 2) + backgroundInternalStroke.toPx(),
                center = Offset(x = this.size.width / 2, y = this.size.height / 2)
            )


            // This is the white circle that appears on the top of the shadow circle
            drawCircle(
                color = backgroundInternalColor,
                radius = (size / 2 - indicatorThickness).toPx(),
                center = Offset(x = this.size.width / 2, y = this.size.height / 2)
            )

            // Convert the dataUsage to angle
            val sweepAngle = (dataUsageAnimate.value) * 360 / 100

            // Foreground indicator
            drawArc(
                color = foregroundIndicatorColor,
                startAngle = -90f,
                sweepAngle = sweepAngle,
                useCenter = false,
                style = Stroke(width = indicatorThickness.toPx(), cap = StrokeCap.Round),
                size = Size(
                    width = (size - indicatorThickness).toPx(),
                    height = (size - indicatorThickness).toPx()
                ),
                topLeft = Offset(
                    x = (indicatorThickness / 2).toPx(),
                    y = (indicatorThickness / 2).toPx()
                )
            )
        }

        //Text("Hol")
        val normal = SpanStyle(
            fontSize = textStyle.fontSize,
        )
        val superscript = SpanStyle(
            baselineShift = BaselineShift.Superscript,
            fontSize = textStyle.fontSize.times(0.5f),
        )
        val subscript = SpanStyle(
            baselineShift = BaselineShift.Subscript,
            fontSize = textStyle.fontSize.times(0.9f)
        )

        Text(
            text = buildAnnotatedString {
                withStyle(normal) {
                    append((dataUsageAnimate.value).toInt().toString())
                }
                withStyle(superscript) {
                    append("%")
                }
            },
            style = textStyle,
        )
    }
}