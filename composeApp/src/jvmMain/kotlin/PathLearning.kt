
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.drawOutline
import androidx.compose.ui.graphics.drawscope.DrawScope.Companion.DefaultBlendMode
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Preview
@Composable
fun PreviewPolygon() {
    Column(
        Modifier.fillMaxSize().padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        val polygonShape = PolygonShape(
            sides = 4,
            rotation = 45f
        )

        Surface(
            shape = polygonShape,
            modifier = Modifier
                .align(CenterHorizontally)
                .size(50.dp).background(Color.Red)
        ) {

        }
    }
}

@Preview
@Composable
fun PreviewCanvasLearning3() {
// Creating a Column Layout
    Column(
        Modifier.fillMaxSize().padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        val borderSize = 25.dp
        val borderColor = Color.Red

        Canvas(modifier = Modifier.fillMaxWidth().aspectRatio(1f)) {
            val rect = Rect(Offset.Zero, size)

            val borderPath = Path().apply {
                moveTo(rect.topLeft.x, rect.topLeft.y)
                // start
                lineTo(rect.topLeft.x, rect.bottomLeft.y)
                // bottom
                lineTo(rect.bottomRight.x, rect.bottomRight.y)
                // end
                lineTo(rect.bottomRight.x, rect.topRight.y)
                //close()
            }

            rotate(180f) {
                drawPath(
                    path = borderPath,
                    color = Color.Black,
                    style = Stroke(
                        width = 5.dp.toPx(),
                        //cap = StrokeCap.Round,
                        pathEffect = PathEffect.cornerPathEffect(20f)
                    ),
                    alpha = 1f,
                    blendMode = DefaultBlendMode,
                )
            }

            return@Canvas

            // Adding a path effect of rounded corners
            drawIntoCanvas { canvas ->
                canvas.drawOutline(
                    //outline = Outline.Generic(trianglePath),
                    outline = Outline.Generic(borderPath),
                    paint = Paint().apply {
                        color = Color.Green
                        pathEffect = PathEffect.cornerPathEffect(20f)
                    }

                )
            }
        }

    }
}


@Preview
@Composable
fun PreviewCanvasLearning2() {
// Creating a Column Layout
    Column(
        Modifier.fillMaxSize().padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        val borderSize = 25.dp
        val borderColor = Color.Red

        Canvas(modifier = Modifier.fillMaxWidth().aspectRatio(1f)) {
            val rect = Rect(Offset.Zero, size)

            val borderSizePx = borderSize.toPx()
            val borderPath = Path().apply {
                moveTo(rect.topLeft.x, rect.topLeft.y)
                // start
                lineTo(rect.topLeft.x, rect.bottomLeft.y)
                // bottom
                lineTo(rect.bottomRight.x, rect.bottomRight.y)
                // end
                lineTo(rect.bottomRight.x, rect.topRight.y)
                close()
            }


            // Adding a path effect of rounded corners
            drawIntoCanvas { canvas ->
                canvas.drawOutline(
                    //outline = Outline.Generic(trianglePath),
                    outline = Outline.Generic(borderPath),
                    paint = Paint().apply {
                        color = Color.Green
                        pathEffect = PathEffect.cornerPathEffect(20f)
                    }

                )
            }
        }

    }
}
@Preview
@Composable
fun PreviewCanvasLearning() {
// Creating a Column Layout
    Column(
        Modifier.fillMaxSize().padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        // Creating a canvas and creating a triangular path
        Canvas(modifier = Modifier.fillMaxWidth().aspectRatio(1f)) {
            val rect = Rect(Offset.Zero, size)

            drawRect(
                color = Color.Blue,
                topLeft = Offset(rect.topCenter.x, rect.topCenter.y),
                size = Size(10f, 10f)
            )
            drawRect(
                color = Color.Red,
                topLeft = Offset(rect.bottomRight.x, rect.bottomRight.y),
                size = Size(10f, 10f)
            )

            drawRect(
                color = Color.Black,
                topLeft = Offset(rect.bottomLeft.x, rect.bottomLeft.y),
                size = Size(10f, 10f)
            )

            val trianglePath = Path().apply {
                moveTo(rect.topCenter.x, rect.topCenter.y)
                lineTo(rect.bottomRight.x, rect.bottomRight.y)
                lineTo(rect.bottomLeft.x, rect.bottomLeft.y)
                close()
            }

            // Adding a path effect of rounded corners
            drawIntoCanvas { canvas ->
                canvas.drawOutline(
                    outline = Outline.Generic(trianglePath),
                    paint = Paint().apply {
                        color = Color.Green
                        pathEffect = PathEffect.cornerPathEffect(rect.maxDimension / 3)
                    }
                )
            }
        }

    }
}


// https://juliensalvi.medium.com/custom-shape-with-jetpack-compose-1cb48a991d42
class TicketShape(private val cornerRadius: Float) : Shape {

    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        return Outline.Generic(
            // Draw your custom path here
            path = drawTicketPath(size = size, cornerRadius = cornerRadius)
        )
    }
}

fun drawTicketPath(size: Size, cornerRadius: Float): Path {
    return Path().apply {
        reset()
        // Top left arc
        arcTo(
            rect = Rect(
                left = -cornerRadius,
                top = -cornerRadius,
                right = cornerRadius,
                bottom = cornerRadius
            ),
            startAngleDegrees = 90.0f,
            sweepAngleDegrees = -90.0f,
            forceMoveTo = false
        )
        lineTo(x = size.width - cornerRadius, y = 0f)
        // Top right arc
        arcTo(
            rect = Rect(
                left = size.width - cornerRadius,
                top = -cornerRadius,
                right = size.width + cornerRadius,
                bottom = cornerRadius
            ),
            startAngleDegrees = 180.0f,
            sweepAngleDegrees = -90.0f,
            forceMoveTo = false
        )
        lineTo(x = size.width, y = size.height - cornerRadius)
        // Bottom right arc
        arcTo(
            rect = Rect(
                left = size.width - cornerRadius,
                top = size.height - cornerRadius,
                right = size.width + cornerRadius,
                bottom = size.height + cornerRadius
            ),
            startAngleDegrees = 270.0f,
            sweepAngleDegrees = -90.0f,
            forceMoveTo = false
        )
        lineTo(x = cornerRadius, y = size.height)
        // Bottom left arc
        arcTo(
            rect = Rect(
                left = -cornerRadius,
                top = size.height - cornerRadius,
                right = cornerRadius,
                bottom = size.height + cornerRadius
            ),
            startAngleDegrees = 0.0f,
            sweepAngleDegrees = -90.0f,
            forceMoveTo = false
        )
        lineTo(x = 0f, y = cornerRadius)
        close()
    }
}


@Preview
@Composable
fun PreviewTicketShape() {

    Text(
        text = "🎉 CINEMA TICKET 🎉",
        style = TextStyle(
            color = Color.White,
            fontSize = 22.sp,
            fontWeight = FontWeight.Black,
        ),
        textAlign = TextAlign.Center,
        modifier = Modifier
            .wrapContentSize()
            .graphicsLayer {
                shadowElevation = 8.dp.toPx()
                shape = TicketShape(24.dp.toPx())
                clip = true
            }
            .background(color = Color.Gray)
            .drawBehind {
                scale(scale = 0.9f) {
                    drawPath(
                        path = drawTicketPath(size = size, cornerRadius = 24.dp.toPx()),
                        color = Color.Red,
                        style = Stroke(
                            width = 2.dp.toPx(),
                            pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f))
                        )
                    )
                }
            }
            .padding(start = 32.dp, top = 64.dp, end = 32.dp, bottom = 64.dp)
    )
}


@Preview
@Composable
fun PreviewDesktop() {
    val moviesList = listOf(
        "Streaming",
        "Popular",
        "On TV",
        "For Recent",
        "In Theaters",
    )
    var expanded by remember { mutableStateOf(true) }
    var selectedMovie by remember { mutableStateOf(moviesList[0]) }

    val corner = 25.dp
    var topCornerShape by remember { mutableStateOf(25.dp) }
    LaunchedEffect(expanded) {
        topCornerShape = if (expanded) {
            0.dp
        } else {
            corner
        }
    }



    Box(
        modifier = Modifier.fillMaxSize().background(Color.White),
        contentAlignment = Alignment.Center
    ) {

        val borderColor = Color.Red
        val borderSize = 25.dp

        // https://medium.com/@banmarkovic/jetpack-compose-bottom-border-8f1662c2aa84
        Text(
            text = "Android",
            modifier = Modifier
                .background(Color.Gray)
                .drawBehind {
                    val borderSizePx = borderSize.toPx()


                    // Bottom
                    drawLine(
                        color = borderColor,
                        start = Offset(0f, size.height),
                        end = Offset(size.width, size.height),
                        strokeWidth = borderSizePx
                    )

                    // Top
                    drawLine(
                        color = borderColor,
                        start = Offset(0f, 0f),
                        end = Offset(size.width, 0f),
                        strokeWidth = borderSizePx
                    )

                    // Start
                    drawLine(
                        color = borderColor,
                        start = Offset(0f, 0f),
                        end = Offset(0f, size.height),
                        strokeWidth = borderSizePx
                    )

                    // End
                    drawLine(
                        color = borderColor,
                        start = Offset(size.width, 0f),
                        end = Offset(size.width, size.height),
                        strokeWidth = borderSizePx
                    )


                    drawRect(
                        color = Color.Blue,
                        topLeft = Offset(0f - borderSizePx / 2, 0f - borderSizePx / 2),
                        size = Size(borderSizePx, borderSizePx)
                    )

                }

                .padding(4.dp),
            fontSize = 100.sp,
            color = Color.White
        )
    }

}
