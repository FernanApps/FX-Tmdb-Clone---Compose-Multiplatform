package pe.fernan.kmp.tmdb.ui.items

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import pe.fernan.kmp.tmdb.domain.model.Result
import pe.fernan.kmp.tmdb.paddingInternal
import pe.fernan.kmp.tmdb.theme.LocalWindowSizeWidth
import pe.fernan.kmp.tmdb.theme.WindowSize
import pe.fernan.kmp.tmdb.titleTextStyle2
import pe.fernan.kmp.tmdb.ui.common.VerticalScrollbarCommon
import pe.fernan.kmp.tmdb.ui.common.rememberScrollbarAdapterCommon
import pe.fernan.kmp.tmdb.ui.components.CardPoster
import pe.fernan.kmp.tmdb.ui.components.DropDownMenuCustom
import pe.fernan.kmp.tmdb.ui.components.cardPosterWidth
import pe.fernan.kmp.tmdb.ui.grid.AdaptiveWithCountColumns
import pe.fernan.kmp.tmdb.ui.grid.FixedSizeWithCountColumns
import pe.fernan.kmp.tmdb.ui.home.HomeViewModel
import pe.fernan.kmp.tmdb.ui.navigation.NavigateRoute
import kotlin.random.Random


private val paddingHorizontal = 16.dp
private val paddingVertical = 8.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemsScreen(
    navigateRoute: NavigateRoute,
    viewModel: HomeViewModel,
    onItemSelected: (Result) -> Unit
) {

    var parentKey by remember { mutableStateOf(navigateRoute.parentKey.key) }
    var routeKey by remember { mutableStateOf(navigateRoute.routeKey.key) }

    val itemState by viewModel.itemsState.collectAsState()

    var title by remember { mutableStateOf("${navigateRoute.parentKey.title} : ${navigateRoute.routeKey.title}") }
    var enabledSearchButton by remember { mutableStateOf(false) }

    val titleSort = "Sort"
    val titleWhereToWatch = "Where To Watch"
    val titleFilters = "Filters"

    var isViewModelCalled by remember { mutableStateOf(false) }
    if (!isViewModelCalled) {
        viewModel.getList(parentKey, routeKey)
        isViewModelCalled = true
    }

    var sortSelectedItemIndex by remember { mutableStateOf(0) }
    val sortItems = listOf(
        "Popularity Descending",
        "Popularity Ascending",
        "Rating Descending",
        "Rating Ascending",
        "Release Date Descending",
        "Release Date Ascending",
        "Title (A-Z)"
    )

    @Composable
    fun FilterContent(modifier: Modifier = Modifier.width(cardWidth)) {
        CardDropMenu(modifier, title = titleSort) {
            Text(
                "Sort Results By",
                color = colorText,
                fontWeight = FontWeight.Light
            )
            Spacer(Modifier.size(5.dp))
            DropDownMenuCustom(
                width = Dp(cardWidth.value - 32f),
                selectedItemIndex = sortSelectedItemIndex,
                items = sortItems,
                onItemSelected = {
                    sortSelectedItemIndex = it
                    enabledSearchButton = true
                },
                itemBackgroundBrush = Brush.linearGradient(
                    colors = listOf(
                        Color.White,
                        Color.White
                    )
                ),
                backgroundBorderColor = Color.LightGray,
                backgroundBorderWidth = 0.25.dp,
                backgroundCorner = 5.dp,
                textStyle = TextStyle(),
                iconColor = Color.Black,
                fixDropMenuPosition = {
                    (it / 4) - 5
                }

            )
        }
        Spacer(modifier = Modifier.size(paddingInternal / 2))
        CardDropMenu(modifier, title = titleWhereToWatch)
        Spacer(modifier = Modifier.size(paddingInternal / 2))
        CardDropMenu(modifier, title = titleFilters)
        Spacer(modifier = Modifier.size(paddingInternal / 2))
        Button(
            onClick = {

            },
            modifier = modifier,
            enabled = enabledSearchButton,
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.secondary
            )
        ) {
            Text(
                text = "Search",
                color = if (!enabledSearchButton) Color.Gray else Color.White,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center,
                modifier = Modifier.weight(1f)
            )

        }
    }

    @Composable
    fun ItemsContent(
        modifier: Modifier = Modifier, showScrollBar: Boolean = true,
        isAdaptiveGridCells: Boolean = true,
        itemHeaderContent: (@Composable () -> Unit)? = null,
    ) {
        val state = rememberLazyGridState()
        val paddingInternal = paddingInternal

        var columns by remember {
            mutableStateOf(0)
        }
        Row {
            LazyVerticalGrid(
                state = state,
                columns = if (isAdaptiveGridCells) AdaptiveWithCountColumns(cardPosterWidth + paddingInternal / 2) {
                    columns = it
                } else FixedSizeWithCountColumns(cardPosterWidth + paddingInternal / 2) {
                    columns = it
                },
                modifier = modifier.weight(1f)
            ) {

                if (itemHeaderContent != null) {
                    item(span = { GridItemSpan(columns) }) {
                        itemHeaderContent()
                    }
                }

                items(
                    items = itemState.itemsList ?: (0..25).toList()
                        .mapIndexed { index, it -> Result(id = index) }
                    ,
                    key = { "${it.id ?: Random.nextInt()}-${it.mediaType}" }
                ) { data ->
                    
                    Column {
                        Row(horizontalArrangement = Arrangement.Center) {
                            Card(
                                elevation = cardElevationDefault,
                                colors = CardDefaults.cardColors(containerColor = Color.White),
                                modifier = modifier
                            ) {
                                CardPoster(
                                    data = data,
                                    loading = itemState.itemsList == null
                                ) {
                                    onItemSelected(it)
                                }

                            }
                            Spacer(Modifier.size(paddingInternal / 2))
                        }
                        Spacer(Modifier.size(paddingInternal / 2))
                    }
                }
            }
            if (showScrollBar) {
                VerticalScrollbarCommon(
                    modifier = Modifier.padding(start = 8.dp).fillMaxHeight(),
                    adapter = rememberScrollbarAdapterCommon(
                        scrollState = state
                    )
                )
            }

        }
    }

    @Composable
    fun TitleContent() {
        Text(
            text = title,
            style = titleTextStyle2,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.primary,
        )
    }

    Column(
        Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background).padding(
            paddingInternal
        )

    ) {
        TitleContent()
        Spacer(Modifier.size(paddingInternal))

        if (LocalWindowSizeWidth.current == WindowSize.Compact) {
            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.Center
            ) {

                ItemsContent(isAdaptiveGridCells = true) {
                    Column(
                        //state = state,
                        modifier = Modifier.fillMaxSize().weight(1f)
                    ) {
                        FilterContent(Modifier.fillMaxWidth())
                        Spacer(modifier = Modifier.size(paddingInternal))
                    }
                }
            }
        } else {
            Row {
                Column {
                    FilterContent()
                }
                Spacer(modifier = Modifier.size(paddingInternal))
                ItemsContent()
            }
        }


    }


}


private val colorText = Color(0xFF212529)
private val cardWidth = 225.dp
private val cardElevation = 3.dp
private val cardElevationDefault
    @Composable get() = CardDefaults.cardElevation(
        cardElevation,
        cardElevation,
        cardElevation,
        cardElevation,
        cardElevation,
        cardElevation
    )

@Composable
private fun CardDropMenu(
    modifier: Modifier,
    title: String,
    itemContent: (@Composable () -> Unit)? = null
) {
    var expand by remember { mutableStateOf(false) }

    Card(
        elevation = cardElevationDefault,
        colors = CardDefaults.cardColors(containerColor = Color.White),
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth().clickable {
                    expand = !expand
                }.padding(horizontal = paddingHorizontal, vertical = paddingVertical + 5.dp)
            ) {
                Text(
                    text = title,
                    color = colorText,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.weight(1f)
                )
                Icon(
                    imageVector = Icons.Filled.ArrowForwardIos,
                    "",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(15.dp).rotate(if (expand) 90f else 0f)
                )
            }
            if (itemContent != null) {
                if (expand) {

                    Divider(
                        modifier = Modifier.fillMaxWidth(),
                        thickness = 0.5.dp,
                        color = colorText.copy(0.5f)
                    )
                    Column(
                        modifier = Modifier.fillMaxWidth()
                            .padding(
                                horizontal = paddingHorizontal, vertical = paddingVertical
                            )
                    ) {
                        itemContent()
                    }
                }

            }
        }


    }
}




