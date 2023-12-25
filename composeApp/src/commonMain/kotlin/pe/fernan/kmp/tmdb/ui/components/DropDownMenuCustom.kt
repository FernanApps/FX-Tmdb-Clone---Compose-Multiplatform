package pe.fernan.kmp.tmdb.ui.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import pe.fernan.kmp.tmdb.ehPath1
import pe.fernan.kmp.tmdb.ui.ext.dpToPx
import pe.fernan.kmp.tmdb.ui.ext.pxToDp

@OptIn(
    ExperimentalMaterial3Api::class, ExperimentalMaterial3WindowSizeClassApi::class,
    ExperimentalFoundationApi::class
)
@Composable
fun DropDownMenuCustom(
    width: Dp = 250.dp,
    backgroundBorderWidth: Dp,
    backgroundCorner: Dp,
    selectedItemIndex: Int = 0,
    onItemSelected: (Int) -> Unit,
    items: List<String>,
    itemBackgroundBrush: Brush,
    backgroundBorderColor: Color,
    textStyle: TextStyle = TextStyle(),
    iconColor: Color = Color.Black,
    fixDropMenuPosition: (Int) -> Int = {
        it / 2
    }
) {
    var isDropDownMenuExpanded by remember { mutableStateOf(false) }
    var offset by remember { mutableStateOf(IntOffset.Zero) }


    val cornerPx = backgroundCorner.dpToPx()

    val ehShapeInv =
        RoundedCornerShape(
            topStart = 0.dp,
            topEnd = 0.dp,
            bottomStart = backgroundCorner,
            bottomEnd = backgroundCorner
        )

    val mainShape = RoundedCornerShape(
        topStart = backgroundCorner,
        topEnd = backgroundCorner,
        bottomStart = if (!isDropDownMenuExpanded) backgroundCorner else 0.dp,
        bottomEnd = if (!isDropDownMenuExpanded) backgroundCorner else 0.dp
    )

    var columnHeightPx by remember {
        mutableStateOf(0)
    }

    Box(contentAlignment = Alignment.Center, modifier = Modifier) {
        Row(
            modifier = Modifier.width(width)
                .background(
                    brush = itemBackgroundBrush, shape = mainShape
                ).then(
                    if (!isDropDownMenuExpanded) {
                        Modifier.border(
                            backgroundBorderWidth,
                            shape = mainShape,
                            color = backgroundBorderColor
                        )
                    } else {
                        Modifier.drawBehind {
                            //scale(scale = 0.9f) {
                            rotate(180f) {
                                drawPath(
                                    path = ehPath1(size = size),
                                    color = backgroundBorderColor,
                                    style = Stroke(
                                        width = backgroundBorderWidth.toPx(),
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
                    //offset = offset.copy(y = (offset.y + columnHeightPx * 1).toInt())
                    isDropDownMenuExpanded = !isDropDownMenuExpanded
                }.onGloballyPositioned { coordinates ->
                    val positionInParent: Offset = coordinates.positionInParent()
                    columnHeightPx = coordinates.size.height
                    offset = IntOffset(
                        positionInParent.x.toInt(),
                        positionInParent.y.toInt() + fixDropMenuPosition(columnHeightPx)
                    )
                },
            verticalAlignment = Alignment.CenterVertically,
        ) {

            Text(
                text = items[selectedItemIndex],
                style = textStyle,
                //fontWeight = FontWeight.SemiBold,
                //color = Color(0xFF032541),
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 5.dp)
            )
            Icon(
                Icons.Filled.ArrowDropDown,
                null,
                Modifier.rotate(if (isDropDownMenuExpanded) 180f else 0f),
                tint = iconColor
            )
        }


    }
    Box {
        MaterialTheme(
            colorScheme = MaterialTheme.colorScheme.copy(surface = Color(0xFFe3f2fd)),
            shapes = MaterialTheme.shapes.copy(
                extraSmall = ehShapeInv,
                small = ehShapeInv,
                medium = ehShapeInv,
                large = ehShapeInv,
                extraLarge = ehShapeInv
            )
        ) {

            DropdownMenu(
                modifier = Modifier
                    .width(width)
                    //.clip(ehShapeInv)
                    .background(
                        brush = itemBackgroundBrush, ehShapeInv
                    ).drawBehind {

                        //scale(scale = 0.9f) {
                        rotate(0f) {
                            drawPath(
                                path = ehPath1(size = size),
                                color = backgroundBorderColor,
                                style = Stroke(
                                    width = backgroundBorderWidth.toPx(),
                                    pathEffect = PathEffect.cornerPathEffect(
                                        cornerPx + cornerPx / 3
                                    )
                                )
                            )
                        }
                    },
                offset = DpOffset(offset.x.pxToDp(), offset.y.pxToDp()),
                expanded = isDropDownMenuExpanded,
                onDismissRequest = { isDropDownMenuExpanded = false }
            ) {

                items.forEachIndexed { index, item ->
                    Text(
                        text = item,
                        style = textStyle,
                        //fontWeight = FontWeight.SemiBold,
                        //color = Color(0xFF032541),
                        modifier = Modifier.fillMaxWidth().clickable {
                            onItemSelected(index)
                            isDropDownMenuExpanded = false
                        }.padding(horizontal = 16.dp, vertical = 5.dp)
                    )
                }

            }
        }

    }
    //ModalNavigationDrawer()

}