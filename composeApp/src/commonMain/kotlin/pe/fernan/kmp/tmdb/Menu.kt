package pe.fernan.kmp.tmdb
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import pe.fernan.kmp.tmdb.ui.ext.dpToPx

fun ehPath1(size: Size): Path {
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
    return borderPath
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownDemo() {
    var expanded by remember { mutableStateOf(false) }
    val items = listOf("A", "B", "C", "D", "E", "F")
    val disabledValue = "B"
    var selectedIndex by remember { mutableStateOf(0) }
    Box(modifier = Modifier.fillMaxSize().wrapContentSize(Alignment.TopStart)) {

        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded },
            modifier = Modifier.fillMaxWidth().background(
                Color.Red
            )
        ) {
            Text(
                items[selectedIndex],
                modifier = Modifier.menuAnchor().background(
                    Color.Gray
                )
            )
            items.forEachIndexed { index, s ->
                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = {
                        expanded = !expanded
                    }
                ) {
                    val disabledText = if (s == disabledValue) {
                        " (Disabled)"
                    } else {
                        ""
                    }
                    Text(text = s + disabledText)
                }
            }
        }
    }
}


@Composable
fun PreviewMenu() {
    var expanded by remember { mutableStateOf(false) }

    var selected by remember { mutableStateOf("Popular") }

    val corner = 15.dp
    val corner2 = 0.dp

    var topCornerShape by remember { mutableStateOf(25.dp) }

    val cornerPx = corner.dpToPx()

    val mainShape = RoundedCornerShape(
        topStart = corner,
        topEnd = corner,
        bottomStart = if (!expanded) corner else 0.dp,
        bottomEnd = if (!expanded) corner else 0.dp
    )

    val items = listOf(
        "Streaming", "On TV", "For Recent", "In Theaters"
    )

    val ehShapeInv =
        RoundedCornerShape(
            topStart = 0.dp,
            topEnd = 0.dp,
            bottomStart = corner,
            bottomEnd = corner
        )



    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {

        Column(modifier = Modifier.width(250.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth()
                    /*
                    .graphicsLayer {
                        shadowElevation = 8.dp.toPx()
                        shape = ehShape
                        clip = true
                    }
                    */
                    .background(
                        brush = Brush.horizontalGradient(
                            colors =
                            listOf(
                                Color(0xFFBEFDCE),
                                Color(0xFF20D5A9)
                            )
                        ), mainShape
                    ).then(
                        if (!expanded) {
                            Modifier.border(2.dp, shape = mainShape, color = Color(0xFF1ED5A9))
                        } else {
                            Modifier.drawBehind {

                                //scale(scale = 0.9f) {
                                rotate(180f) {
                                    drawPath(
                                        path = ehPath1(size = size),
                                        color = Color(0xFF1ED5A9),
                                        style = Stroke(
                                            width = 2.dp.toPx(),
                                            pathEffect = PathEffect.cornerPathEffect(
                                                cornerPx + cornerPx / 3
                                            )
                                        )
                                    )
                                }
                                //}
                            }
                        }
                    )
                    .clip(mainShape)
                    .clickable {
                        expanded = !expanded
                    }
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .padding(horizontal = 15.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = selected,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF032541)
                )
                Icon(Icons.Filled.ArrowDropDown, null, Modifier.rotate(if (expanded) 180f else 0f))
            }
            // Items
            if (expanded) {

                Column(
                    modifier = Modifier.fillMaxWidth().background(
                        brush = Brush.horizontalGradient(
                            colors =
                            listOf(
                                Color(0xFFBEFDCE),
                                Color(0xFF20D5A9)
                            )
                        ), ehShapeInv
                    ).drawBehind {
                        //scale(scale = 0.9f) {
                        rotate(0f) {
                            drawPath(
                                path = ehPath1(size = size),
                                color = Color(0xFF1ED5A9),
                                style = Stroke(
                                    width = 2.dp.toPx(),
                                    pathEffect = PathEffect.cornerPathEffect(
                                        cornerPx + cornerPx / 3
                                    )
                                )
                            )
                        }
                    }


                        //}
                        .padding(start = 15.dp, end = 15.dp, bottom = 8.dp)
                ) {
                    items.forEach { item ->
                        Text(
                            text = item,
                            fontWeight = FontWeight.SemiBold,
                            color = Color(0xFF032541),
                            modifier = Modifier.fillMaxWidth().clickable {
                                selected = item
                                expanded = false
                            }.padding(horizontal = 16.dp, vertical = 8.dp)
                        )
                    }
                }
            }
        }


    }
}

