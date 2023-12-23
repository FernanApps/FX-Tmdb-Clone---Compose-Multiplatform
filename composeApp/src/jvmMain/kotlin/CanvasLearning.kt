import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StampedPathEffectStyle
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.unit.dp


@Preview
@Composable
fun DrawBasic() {
    Column {

        // DrawCircle
        Canvas(
            modifier = Modifier
                .size(200.dp)
                .background(Color.White)
                .border(2.dp, color = Color.Blue)
        ) {
            drawCircle(color = Color.Red, radius = 100f)
        }

        // DrawRectangle
        Canvas(
            modifier = Modifier
                .size(200.dp)
                .background(Color.White)
                .border(2.dp, color = Color.Blue)
        ) {
            drawRect(color = Color.Green, size = size)
        }

        // DrawLine
        Canvas(
            modifier = Modifier
                .size(200.dp)
                .background(Color.White)
                .border(2.dp, color = Color.Red)
        ) {
            drawLine(
                color = Color.Blue,
                start = Offset.Zero,
                end = Offset(size.width, size.height),
                strokeWidth = 4f
            )
        }


    }
}

@Preview
@Composable
fun DrawBasic2() {
    Column {
        // DrawTriangle
        Canvas(
            modifier = Modifier
                .size(200.dp)
        ) {
            val path = Path().apply {
                moveTo(size.width / 2, 0f)
                lineTo(size.width, size.height)
                lineTo(0f, size.height)
                close()
            }
            drawPath(path = path, color = Color.Magenta)
        }
        // DrawArc
        val sizeCanvas = 200.dp
        Canvas(modifier = Modifier.size(sizeCanvas)) {


            drawRect(
                color = Color.Black,
                topLeft = Offset.Zero,
                size = Size(sizeCanvas.toPx(), sizeCanvas.toPx())
            )
            drawRect(
                color = Color.White,
                topLeft = Offset(
                    x = (sizeCanvas / 2).toPx(),
                    y = ((sizeCanvas / 2)).toPx()
                ),
                size = Size(10f, 10f)
            )

            drawArc(
                //color = Color.Cyan,
                brush = Brush.horizontalGradient(colors = listOf(Color.Yellow, Color.Green)),
                startAngle = 180f,
                sweepAngle = 90f,
                useCenter = true,
                //topLeft = Offset.Zero,
                topLeft = Offset(
                    x = (sizeCanvas / 2).toPx(),
                    y = ((sizeCanvas / 2)).toPx()
                ),
                size = size,
                //style = Stroke(width = 8.dp.toPx())
                //style = Fill
                style = Stroke(
                    width = 8.dp.toPx(),
                    /*pathEffect = PathEffect.cornerPathEffect(5f)
                    pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f))
                    pathEffect = PathEffect.chainPathEffect(
                        PathEffect.dashPathEffect(
                            intervals = floatArrayOf(20f, 10f),
                            0f
                        ),
                        PathEffect.cornerPathEffect(
                            80f
                        )
                    )

                    pathEffect = PathEffect.stampedPathEffect(
                        Path().apply {
                            moveTo(-10f, 0f)
                            arcTo(
                                Rect(-10f, -10f, 10f, 10f),
                                180f,
                                180f,
                                false
                            );
                        }, 20f, 0f, StampedPathEffectStyle.Morph
                    )
                            */
                    pathEffect = PathEffect.stampedPathEffect(
                        Path().apply {
                            // heartPath
                            val width = 20f
                            val height = 20f
                            moveTo(width / 2, height / 4)
                            cubicTo(width / 4, 0f, 0f, height / 3, width / 4, height / 2)
                            lineTo(width / 2, height * 3 / 4)
                            lineTo(width * 3 / 4, height / 2)
                            cubicTo(width, height / 3, width * 3 / 4, 0f, width / 2, height / 4)
                        }, 20f, 10f, StampedPathEffectStyle.Translate
                    )

                )
            )
        }

        // ApplyClip
        Canvas(modifier = Modifier.size(200.dp)) {
            clipPath(Path().apply {
                addOval(Rect(Offset.Zero, size)) }) {
                drawRect(color = Color.Yellow)
            }
        }
    }
}
@Preview
@Composable
fun DrawBasic3() {
    // ApplyTransform
    Column {
        Canvas(modifier = Modifier.size(200.dp)) {
            rotate(45f) {
                drawRect(color = Color.Blue)
            }
        }

        // DrawWithShader
        Canvas(modifier = Modifier.size(200.dp)) {
            val shader = Brush.linearGradient(
                colors = listOf(Color.Red, Color.Blue),
                start = Offset.Zero,
                end = Offset(size.width, size.height)
            )
            drawRect(brush = shader)
        }

        // Custom Progress Bar
        val progress = 50f
        val stroke = 8.dp
        val color = Color.Blue
        Canvas(modifier = Modifier.size(100.dp)) {
            val innerRadius = (size.minDimension - stroke.toPx()) / 2
            val halfSize = size / 2.0f
            val topLeft = Offset(
                x = halfSize.width - innerRadius,
                y = halfSize.height - innerRadius
            )
            val size = Size(
                width = innerRadius * 2,
                height = innerRadius * 2
            )
            val startAngle = -90f
            val sweep = progress * 360f
            drawArc(
                color = color,
                startAngle = startAngle,
                sweepAngle = sweep,
                topLeft = topLeft,
                size = size,
                useCenter = false,
                style = Stroke(width = stroke.toPx())
            )
        }
    }




}