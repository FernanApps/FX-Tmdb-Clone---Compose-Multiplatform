
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.seiko.imageloader.rememberImagePainter
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import pe.fernan.kmp.tmdb.paddingInternal
import pe.fernan.kmp.tmdb.subTitleTextStyle
import pe.fernan.kmp.tmdb.theme.LocalWindowSizeClass
import pe.fernan.kmp.tmdb.ui.common.HorizontalScrollbarCommon
import pe.fernan.kmp.tmdb.ui.common.defaultScrollbarStyleCommon
import pe.fernan.kmp.tmdb.ui.common.rememberScrollbarAdapterCommon
import pe.fernan.kmp.tmdb.ui.components.AnimatedTab
import pe.fernan.kmp.tmdb.ui.components.DropDownMenuCustom
import pe.fernan.kmp.tmdb.ui.ext.mouseClickHorizontalScroll
import pe.fernan.kmp.tmdb.ui.ext.pxToDp

@OptIn(ExperimentalResourceApi::class)
@Composable
fun <T> HomeSection(
    title: String,
    titleColor: Color = Color.Black,
    background: String? = null,
    backgroundGradient: Boolean = false,
    tabItems: List<String>,
    tabBackgroundBorderColor: Color = MaterialTheme.colorScheme.primary,
    tabBackgroundColor: Color = Color.White,
    tabItemBackgroundBrush: Brush = Brush.linearGradient(
        colors = listOf(
            MaterialTheme.colorScheme.primary,
            MaterialTheme.colorScheme.primary
        )
    ),
    tabItemContentStyle: TextStyle = TextStyle(
        brush = Brush.linearGradient(
            colors = listOf(
                MaterialTheme.colorScheme.primary,
                MaterialTheme.colorScheme.primary,
            ),
        ),
        fontWeight = FontWeight.SemiBold,
    ),
    tabItemContentSelectedStyle: TextStyle = TextStyle(
        brush = Brush.linearGradient(
            colors = listOf(
                MaterialTheme.colorScheme.tertiary,
                MaterialTheme.colorScheme.secondary
            )
        ),
        fontWeight = FontWeight.Bold,
    ),
    tabSelectedItemIndex: Int,
    onTabItemSelected: (Int) -> Unit,
    itemList: List<T>,
    itemContent: @Composable LazyItemScope.(data: T) -> Unit,

    ) {
    if (itemList.isEmpty()) {
        return
    }

    var lazyHeight by remember { mutableStateOf(0) }
    val density = LocalDensity.current
    val stateList = rememberLazyListState()

    Box(
        modifier = Modifier.then(
            if (lazyHeight > 0) {
                Modifier.height(lazyHeight.pxToDp() + 10.dp)
            } else Modifier
        )
    ) {

        if (background != null) {
            val blueColor = Color(0xFF022541)
            val blueColor2 = Color(0xFF016B9A)

            val colorStops = arrayOf(
                0.15f to blueColor.copy(0.8f),
                0.75f to blueColor2.copy(0.7f),
                1f to blueColor2.copy(0.6f)
            )

            val gradient = Brush.horizontalGradient(colorStops = colorStops)


            val painter = if (background.startsWith("http")) {
                rememberImagePainter(background)
            } else {
                painterResource(background)
            }

            if (backgroundGradient) {
                Image(
                    painter = painter,
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize()
                        .align(Alignment.BottomCenter),
                    contentScale = ContentScale.Crop,
                    colorFilter = ColorFilter.tint(
                        blueColor2, blendMode = BlendMode.Color
                    ),
                )
                Box(
                    modifier = Modifier.fillMaxSize().background(gradient)
                )
            } else {
                Image(
                    painter = painter,
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize()
                        .align(Alignment.BottomCenter),
                    contentScale = ContentScale.Crop,
                )
            }
        }

        Column(modifier = Modifier.onGloballyPositioned { coordinates ->
            lazyHeight = coordinates.size.height

            return@onGloballyPositioned
            if (lazyHeight == 0) {
                lazyHeight = coordinates.size.height
            } else if (coordinates.size.height < lazyHeight) {
                // Fix For ReComposition :(
                lazyHeight = coordinates.size.height
            }
        }) {
            Row(
                modifier = Modifier.padding(
                    start = paddingInternal, top = paddingInternal
                ), verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = title,
                    color = titleColor,
                    style = subTitleTextStyle,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(Modifier.width(20.dp))

                if (LocalWindowSizeClass.current.widthSizeClass == WindowWidthSizeClass.Compact) {

                    DropDownMenuCustom(
                        width = 150.dp,
                        selectedItemIndex = tabSelectedItemIndex,
                        items = tabItems,
                        onItemSelected = {
                            onTabItemSelected(it)
                        },
                        itemBackgroundBrush = tabItemBackgroundBrush,
                        backgroundBorderColor = tabBackgroundBorderColor,
                        backgroundBorderWidth = 0.5.dp,
                        backgroundCorner = 15.dp,
                        textStyle = tabItemContentSelectedStyle,
                        iconColor = if (background?.startsWith("http") == true) {
                            Color.Black
                        } else {
                            Color.White
                        }
                    )

                } else {

                    AnimatedTab(modifier = Modifier.height(30.dp).width(125.dp * tabItems.size),
                        selectedItemIndex = tabSelectedItemIndex,
                        onSelectedTab = {
                            onTabItemSelected(it)
                        },
                        backgroundColor = tabBackgroundColor,
                        shape = RoundedCornerShape(30.dp),
                        backgroundBorderWidth = 0.5.dp,
                        backgroundBorderColor = tabBackgroundBorderColor,
                        itemBackgroundBrush = tabItemBackgroundBrush,
                        items = tabItems,
                        itemContentSelected = { text ->
                            Text(
                                text = text,
                                style = tabItemContentSelectedStyle,
                            )
                        },
                        itemContent = { text ->
                            Text(
                                text = text,
                                style = tabItemContentStyle,
                                modifier = Modifier.padding(end = 15.dp)
                            )
                        })
                }


            }

            Spacer(Modifier.height(paddingInternal / 2))

            Column {

                LazyRow(
                    state = stateList,
                    modifier = Modifier.mouseClickHorizontalScroll(stateList)
                        .padding(start = paddingInternal)
                ) {
                    items(itemList) {
                        itemContent(it)
                    }
                }

                HorizontalScrollbarCommon(
                    modifier = Modifier.fillMaxWidth()
                        .padding(start = paddingInternal, top = 12.dp, end = 12.dp),
                    adapter = rememberScrollbarAdapterCommon(stateList),
                    style = defaultScrollbarStyleCommon(background)
                )
            }


        }
    }

}