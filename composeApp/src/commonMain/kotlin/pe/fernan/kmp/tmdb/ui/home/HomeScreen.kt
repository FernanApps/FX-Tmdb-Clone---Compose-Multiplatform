package pe.fernan.kmp.tmdb.ui.home


import HomeSection
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.seiko.imageloader.rememberImagePainter
import io.github.aakira.napier.Napier
import kotlinx.datetime.Clock
import pe.fernan.kmp.tmdb.domain.model.MediaType
import pe.fernan.kmp.tmdb.domain.model.Result
import pe.fernan.kmp.tmdb.domain.model.TimeWindows
import pe.fernan.kmp.tmdb.paddingInternal
import pe.fernan.kmp.tmdb.subTitleTextStyle
import pe.fernan.kmp.tmdb.titleTextStyle
import pe.fernan.kmp.tmdb.ui.common.LocalCurrentSize
import pe.fernan.kmp.tmdb.ui.common.VerticalScrollbarCommon
import pe.fernan.kmp.tmdb.ui.common.rememberScrollbarAdapterCommon
import pe.fernan.kmp.tmdb.ui.components.CardHorizontalPoster
import pe.fernan.kmp.tmdb.ui.components.CardPoster
import pe.fernan.kmp.tmdb.ui.components.CustomTextField
import pe.fernan.kmp.tmdb.ui.main.itemMovie
import pe.fernan.kmp.tmdb.ui.main.itemSeriesTV
import pe.fernan.kmp.tmdb.utils.Constant





@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun HomeScreen(viewModel: HomeViewModel, onClickSearch: (String) -> Unit, onItemClick: (Result) -> Unit) {
    Napier.i("HomeScreen init ${Clock.System.now()}")
    val homeState by viewModel.homeState.collectAsState()

    // Section TRENDING
    val trendingTitle = "Trending"
    var trendingSelectedItemIndexTab by remember { mutableStateOf(0) }
    val trendingKeys = mapOf(
        TimeWindows.DAY.value to "Today",
        TimeWindows.WEEK.value to "This Week"
    )

    // Sections DISCOVER
    val discoverTitle = "Discover"
    var discoverSelectedItemIndexTab by remember { mutableStateOf(0) }
    val discoverKeys = mapOf(
        MediaType.MOVIE.value to "Movie",
        MediaType.TV.value to "TV"

    )

    // Sections MOVIE LISTS
    val movieListTitle = itemMovie.main.title
    var movieListSelectedItemIndexTab by remember { mutableStateOf(0) }
    val movieListKeys = itemMovie.routes

    // Sections TV SERIES LISTS
    val tvSeriesListTitle = itemSeriesTV.main.title
    var tvSeriesListSelectedItemIndexTab by remember { mutableStateOf(0) }
    val tvSeriesListKeys = itemSeriesTV.routes


    var isViewModelCalled by remember { mutableStateOf(false) }
    if (!isViewModelCalled) {
        viewModel.getTrendingList(trendingKeys.keys.first())
        viewModel.getDiscover(discoverKeys.keys.first())
        viewModel.getMovieList(movieListKeys.first().key)
        viewModel.getTVSeriesList(tvSeriesListKeys.first().key)
        isViewModelCalled = true
    }


    var search by remember { mutableStateOf("") }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {

        val state = rememberLazyListState()


        LazyColumn(
            state = state,
            //modifier = Modifier.fillMaxSize().padding(it)
        ) {

            item {

                SectionInit(
                    backgroundPath = homeState.mainBackgroundPath ?: "",
                    search = search, onSearchValueChange = {
                        search = it
                    }, onClickSearch = {
                        onClickSearch(search)
                    })

                HomeSection(
                    title = trendingTitle,
                    background = "lines.xml",
                    tabItems = trendingKeys.values.toList(),
                    tabSelectedItemIndex = trendingSelectedItemIndexTab,
                    onTabItemSelected = { tabItemSelected ->
                        trendingSelectedItemIndexTab = tabItemSelected
                        viewModel.getTrendingList(
                            trendingKeys.entries.find {
                                it.value == trendingKeys.values.toList()[trendingSelectedItemIndexTab]
                            }!!.key
                        )
                    },
                    itemList = homeState.trendingList ?: (0..25).toList()
                        .map { Result() },
                    itemContent = { data ->
                        Row {
                            CardPoster(
                                data = data,
                                loading = homeState.trendingList == null
                            ) {
                                onItemClick(data)
                            }
                            Spacer(Modifier.size(paddingInternal / 2))
                        }
                    }

                )

                Spacer(Modifier.size(10.dp))
                HomeSection(
                    title = discoverTitle,
                    titleColor = Color.White,
                    background = Constant.IMAGE_BASE_PATH_CARD_HORIZONTAL_LARGE + homeState.discoverBackgroundPath,
                    backgroundGradient = true,
                    tabItems = discoverKeys.values.toList(),
                    tabSelectedItemIndex = discoverSelectedItemIndexTab,
                    onTabItemSelected = { tabItemSelected ->
                        discoverSelectedItemIndexTab = tabItemSelected
                        viewModel.getDiscover(
                            discoverKeys.entries.find {
                                it.value == discoverKeys.values.toList()[discoverSelectedItemIndexTab]
                            }!!.key
                        )
                    },
                    tabItemBackgroundBrush = Brush.linearGradient(
                        colors =
                        listOf(
                            Color(0xFFBEFDCE),
                            Color(0xFF20D5A9)
                        )
                    ),
                    tabBackgroundBorderColor = Color(0xFF1ED5A9),
                    tabBackgroundColor = Color.Transparent,
                    tabItemContentSelectedStyle = TextStyle(
                        brush = Brush.linearGradient(
                            colors = listOf(
                                MaterialTheme.colorScheme.primary,
                                MaterialTheme.colorScheme.primary,

                                )
                        ),
                        fontWeight = FontWeight.SemiBold,
                    ),
                    tabItemContentStyle = TextStyle(
                        brush = Brush.linearGradient(
                            colors = listOf(Color.White, Color.White),
                        ),
                        fontWeight = FontWeight.Bold,
                    ),
                    itemList = homeState.discoverList ?: (0..25).toList()
                        .map { Result() },
                    itemContent = { data ->
                        Row {
                            CardHorizontalPoster(
                                data = data,
                                loading = homeState.discoverList == null,
                                onMouseover = {
                                    viewModel.setBackgroundDiscover(it)
                                }, onClick = {

                                }
                            )
                            Spacer(Modifier.size(paddingInternal / 2))
                        }
                    }
                )

                // Section

                Spacer(Modifier.size(10.dp))
                HomeSection(
                    title = movieListTitle,
                    tabItems = movieListKeys.toList().map { it.title },
                    tabSelectedItemIndex = movieListSelectedItemIndexTab,
                    onTabItemSelected = { tabItemSelected ->
                        movieListSelectedItemIndexTab = tabItemSelected
                        viewModel.getMovieList(
                            movieListKeys.toList().find { keyRoute ->
                                keyRoute.key == movieListKeys.toList()[movieListSelectedItemIndexTab].key
                            }!!.key
                        )
                    },
                    itemList = homeState.movieList ?: (0..25).toList()
                        .map { Result() },
                    itemContent = { data ->
                        Row {
                            CardPoster(
                                data = data,
                                loading = homeState.movieList == null
                            ) {

                            }
                            Spacer(Modifier.size(paddingInternal / 2))
                        }
                    }

                )

                // Section


                Spacer(Modifier.size(10.dp))
                HomeSection(
                    title = tvSeriesListTitle,
                    tabItems = tvSeriesListKeys.toList().map { it.title },
                    tabSelectedItemIndex = tvSeriesListSelectedItemIndexTab,
                    onTabItemSelected = { tabItemSelected ->
                        tvSeriesListSelectedItemIndexTab = tabItemSelected
                        viewModel.getTVSeriesList(
                            tvSeriesListKeys.toList().find { keyValue ->
                                keyValue.key == tvSeriesListKeys.toList()[tvSeriesListSelectedItemIndexTab].key
                            }!!.key
                        )
                    },
                    itemList = homeState.tvSeriesList ?: (0..25).toList()
                        .map { Result() },
                    itemContent = { data ->
                        Row {
                            CardPoster(
                                data = data,
                                loading = homeState.tvSeriesList == null
                            ) {

                            }
                            Spacer(Modifier.size(paddingInternal / 2))
                        }
                    }

                )
                Spacer(Modifier.size(10.dp))

            }
            return@LazyColumn


        }

        VerticalScrollbarCommon(
            modifier = Modifier.align(Alignment.CenterEnd).fillMaxHeight(),
            adapter = rememberScrollbarAdapterCommon(
                scrollState = state
            )
        )
    }


}


@Composable
fun SectionInit(
    backgroundPath: String,
    search: String,
    onSearchValueChange: (String) -> Unit,
    onClickSearch: () -> Unit
) {
    Box(modifier = Modifier.fillMaxWidth()) {

        val backDrop = Constant.IMAGE_BASE_PATH_CARD_HORIZONTAL_SUPER_LARGE + backgroundPath
        val painter = rememberImagePainter(backDrop)
        var sizeImage by remember { mutableStateOf(IntSize.Zero) }

        val blueColor = Color(0xFF022541)
        val blueColor2 = Color(0xFF016B9A)


        val colorStops = arrayOf(
            0.15f to blueColor.copy(0.8f),
            0.75f to blueColor2.copy(0.7f),
            1f to blueColor2.copy(0.6f)
        )

        val gradient = Brush.horizontalGradient(colorStops = colorStops)

        val localCurrentSize = LocalCurrentSize()

        Box(
            modifier = Modifier.fillMaxWidth().height(localCurrentSize.height * 0.4f)
        ) {


            Image(painter = painter,
                contentDescription = "image",
                contentScale = ContentScale.Crop,
                colorFilter = ColorFilter.tint(
                    blueColor2, blendMode = BlendMode.Color
                ),
                modifier = Modifier.fillMaxSize().onGloballyPositioned {
                    sizeImage = it.size
                })
            Box(modifier = Modifier.matchParentSize().background(gradient))
        }

        Column(modifier = Modifier.padding(paddingInternal)) {
            Text(
                text = "Welcome",
                style = titleTextStyle,
                fontWeight = FontWeight.ExtraBold,
                color = MaterialTheme.colorScheme.onPrimary
            )
            Text(
                text = "Millions of movies, TV shows and people to discover. Explore now.",
                style = subTitleTextStyle,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onPrimary,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis

            )
            Spacer(Modifier.size(paddingInternal / 1.5f))

            val shape = RoundedCornerShape(25.dp)
            //val hintTextSearch = if(LocalWindowSizeClass.current)
            CustomTextField(modifier = Modifier.fillMaxWidth(),
                value = search,
                onValueChange = onSearchValueChange,
                shape = shape,
                label = {
                    Text(
                        "Search for a movie, tv show, person......", color = Color.LightGray,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                trailingIcon = {
                    val gradientColors = listOf(
                        MaterialTheme.colorScheme.secondary, MaterialTheme.colorScheme.tertiary
                    )
                    Button(
                        modifier = Modifier.padding(0.dp),
                        onClick = {
                            onClickSearch()
                        },
                        contentPadding = PaddingValues(0.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Transparent
                        ),
                        //shape = RoundedCornerShape(cornerRadius)
                    ) {

                        Box(
                            modifier = Modifier.background(
                                brush = Brush.linearGradient(colors = gradientColors), shape = shape
                            ).padding(horizontal = 16.dp, vertical = 10.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "Search", color = Color.White
                            )
                        }
                    }
                })

        }
    }
}











