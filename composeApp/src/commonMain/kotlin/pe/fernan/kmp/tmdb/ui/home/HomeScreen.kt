package pe.fernan.kmp.tmdb.ui.home


import HomeSection
import androidx.compose.foundation.Image
import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import com.seiko.imageloader.rememberImagePainter
import io.github.aakira.napier.Napier
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import org.jetbrains.compose.resources.ExperimentalResourceApi
import pe.fernan.kmp.tmdb.domain.model.MediaType
import pe.fernan.kmp.tmdb.domain.model.MovieListType
import pe.fernan.kmp.tmdb.domain.model.Result
import pe.fernan.kmp.tmdb.domain.model.TVSeriesListType
import pe.fernan.kmp.tmdb.domain.model.TimeWindows
import pe.fernan.kmp.tmdb.openUrl
import pe.fernan.kmp.tmdb.paddingInternal
import pe.fernan.kmp.tmdb.subTitleTextStyle
import pe.fernan.kmp.tmdb.theme.AppTheme
import pe.fernan.kmp.tmdb.theme.LocalThemeIsDark
import pe.fernan.kmp.tmdb.theme.LocalWindowSizeClass
import pe.fernan.kmp.tmdb.titleTextStyle
import pe.fernan.kmp.tmdb.ui.common.LocalCurrentSize
import pe.fernan.kmp.tmdb.ui.components.CardHorizontalPoster
import pe.fernan.kmp.tmdb.ui.components.CardPoster
import pe.fernan.kmp.tmdb.ui.components.CustomTextField
import pe.fernan.kmp.tmdb.utils.Constant
import pe.fernan.kmp.tmdb.utils.TimeExt.now


val menuMovies = "Movies" to listOf("Popular", "Now Playing", "Upcoming", "Top Rated")
val menuTVShows = "TVShows" to listOf("Popular", "Airing Today", "On TV", "Top Rated")
val menuPeople = "People" to listOf("Popular People")
val menuMore = "People" to listOf("Discussions", "Leaderboard", "Support", "Api")
val menuAdd = "Add" to listOf("Can't find a movie or TV show? Login to create it")

val menuList = listOf(
    menuMovies,
    menuTVShows,
    menuPeople,
    menuMore,
    menuAdd
)


@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun HomeScreen(viewModel: HomeViewModel) {
    Napier.i("HomeScreen init ${Clock.System.now()}")
    val homeState by viewModel.homeState.collectAsState()

    // Section TRENDING
    val trendingTitle = "Trending"
    var trendingSelectedItemIndexTab by remember { mutableStateOf(0) }
    val trendingKeys = mapOf(
        TimeWindows.DAY to "Today",
        TimeWindows.WEEK to "This Week"
    )

    // Sections DISCOVER
    val discoverTitle = "Discover"
    var discoverSelectedItemIndexTab by remember { mutableStateOf(0) }
    val discoverKeys = mapOf(
        MediaType.MOVIE to "Movie",
        MediaType.TV to "TV"

    )


    // Sections MOVIE LISTS
    val movieListTitle = "Movie"
    var movieListSelectedItemIndexTab by remember { mutableStateOf(0) }
    val movieListKeys = mapOf(
        MovieListType.NOW_PLAYING to "Now Playing",
        MovieListType.POPULAR to "Popular",
        MovieListType.TOP_RATED to "Top Rated",
        MovieListType.UPCOMING to "Upcoming",
    )

    // Sections TV SERIES LISTS
    val tvSeriesListTitle = "TV Series"
    var tvSeriesListSelectedItemIndexTab by remember { mutableStateOf(0) }
    val tvSeriesListKeys = mapOf(
        TVSeriesListType.AIRING_TODAY to "Airing Today",
        TVSeriesListType.ON_THE_AIR to "On The Air",
        TVSeriesListType.POPULAR to "Popular",
        TVSeriesListType.TOP_RATED to "Top Rated",
    )

    var isViewModelCalled by remember { mutableStateOf(false) }
    if (!isViewModelCalled) {
        viewModel.getTrendingList(trendingKeys.keys.first())
        viewModel.getDiscover(discoverKeys.keys.first())
        viewModel.getMovieList(movieListKeys.keys.first())
        viewModel.getTVSeriesList(tvSeriesListKeys.keys.first())
        isViewModelCalled = true
    }

    //iewModel.getTrendingList(trendingKeys.keys.first())
    //viewModel.getDiscover(discoverKeys.keys.first())
    //viewModel.getMovieList(movieListKeys.keys.first())
    //viewModel.getTVSeriesList(tvSeriesListKeys.keys.first())


    val now = LocalDateTime.now()
    val dateNow = with(now) {
        "${dayOfMonth}/${monthNumber}/$year - $hour : $minute : $second "
    }
    var popupControl by remember { mutableStateOf(false) }
    var popupItem by remember { mutableStateOf(Pair<String, List<String>>("", listOf())) }
    var popupOffset by remember { mutableStateOf(IntOffset.Zero) }
    var search by remember { mutableStateOf("") }


    val materialBlue700 = MaterialTheme.colorScheme.primary


    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()
    val drawerItemList = menuList
    val drawerSelectedItem = remember { mutableStateOf(drawerItemList.first()) }


    Scaffold(topBar = {
        TopAppBar(
            title = {
                Header(onExpandMenuClick = { intOffset, subMenu ->
                    popupItem = subMenu
                    popupControl = if (popupControl) {
                        popupOffset != intOffset
                    } else {
                        true
                    }
                    popupOffset = intOffset
                }, onMenuClick = {
                    when (it) {
                        MenuItem.Home -> {
                            coroutineScope.launch {
                                if (drawerState.isOpen) {
                                    drawerState.close()
                                } else if (drawerState.isClosed) {
                                    drawerState.open()
                                }
                            }
                        }

                        MenuItem.Join -> {}
                        MenuItem.Language -> {}
                        MenuItem.Login -> {}
                        MenuItem.Search -> {}
                    }
                })

            }, colors = TopAppBarDefaults.topAppBarColors(
                containerColor = materialBlue700
            )
        )
    }, content = {

        Box(
            modifier = Modifier.fillMaxSize().padding(it)
        ) {

            ModalNavigationDrawer(
                drawerState = drawerState,
                drawerContent = {
                    ModalDrawerSheet(
                        drawerContainerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.97f)
                    ) {
                        Column(
                            modifier = Modifier.fillMaxHeight()
                                .verticalScroll(rememberScrollState()).padding(paddingInternal / 2)
                        ) {
                            drawerItemList.forEachIndexed { index, item ->
                                var isExpandInternal by remember(key1 = "$index-$item") {
                                    mutableStateOf(false)
                                }
                                Column(
                                    modifier = Modifier
                                ) {
                                    Text(
                                        text = item.first,
                                        style = MaterialTheme.typography.titleMedium,
                                        color = MaterialTheme.colorScheme.onPrimary,
                                        fontWeight = FontWeight.SemiBold,
                                        modifier = Modifier.clickable {
                                            if (item.second.isNotEmpty()) {
                                                isExpandInternal = !isExpandInternal
                                            } else {
                                                coroutineScope.launch {
                                                    drawerState.close()
                                                }

                                            }
                                        }.padding(vertical = 8.dp, horizontal = 16.dp)
                                    )
                                    if (isExpandInternal) {
                                        Column {
                                            item.second.forEachIndexed { index, text ->
                                                Text(
                                                    text = text,
                                                    style = MaterialTheme.typography.titleSmall,
                                                    color = MaterialTheme.colorScheme.onPrimary,
                                                    fontWeight = FontWeight.Normal,
                                                    modifier = Modifier.clickable {

                                                    }.padding(vertical = 5.dp, horizontal = 16.dp)

                                                )
                                            }
                                        }
                                        Spacer(Modifier.size(8.dp))
                                    }
                                }


                            }

                        }
                    }
                }
            ) {


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
                                search = search
                            ) {
                                search = it
                            }

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
                                tabItems = movieListKeys.values.toList(),
                                tabSelectedItemIndex = movieListSelectedItemIndexTab,
                                onTabItemSelected = { tabItemSelected ->
                                    movieListSelectedItemIndexTab = tabItemSelected
                                    viewModel.getMovieList(
                                        movieListKeys.entries.find {
                                            it.value == movieListKeys.values.toList()[movieListSelectedItemIndexTab]
                                        }!!.key
                                    )
                                },
                                itemList = homeState.movieList ?: (0..25).toList().map { Result() },
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
                                tabItems = tvSeriesListKeys.values.toList(),
                                tabSelectedItemIndex = tvSeriesListSelectedItemIndexTab,
                                onTabItemSelected = { tabItemSelected ->
                                    tvSeriesListSelectedItemIndexTab = tabItemSelected
                                    viewModel.getTVSeriesList(
                                        tvSeriesListKeys.entries.find {
                                            it.value == tvSeriesListKeys.values.toList()[tvSeriesListSelectedItemIndexTab]
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

                    VerticalScrollbar(
                        modifier = Modifier.align(Alignment.CenterEnd).fillMaxHeight(),
                        adapter = rememberScrollbarAdapter(
                            scrollState = state
                        )
                    )
                }

            }
        }


    }, bottomBar = {
        return@Scaffold
        BottomAppBar(containerColor = materialBlue700) {
            /**
            Group	    horizontal	vertical
            Compact	    < 600 dp	< 480 dp
            Medium	    < 840 dp	< 900 dp
            Expanded	≥ 840 dp	≥ 900 dp

             */
            val windowSizeClass = calculateWindowSizeClass()
            when (windowSizeClass.heightSizeClass) {
                WindowHeightSizeClass.Compact -> {}
                WindowHeightSizeClass.Medium -> {}
                WindowHeightSizeClass.Expanded -> {}
            }
            when (windowSizeClass.widthSizeClass) {
                WindowWidthSizeClass.Compact -> {}
                WindowWidthSizeClass.Medium -> {}
                WindowWidthSizeClass.Expanded -> {}
            }

            Text(
                "height = ${windowSizeClass.heightSizeClass}" + "\nwidth = ${windowSizeClass.widthSizeClass}",
                color = Color.White
            )
        }
    })

    if (popupControl) {
        Box(modifier = Modifier.offset { popupOffset }.fillMaxSize()) {

            Popup(properties = PopupProperties()) {
                LazyColumn(
                    modifier = Modifier
                        //.offset {
                        //    popupOffset
                        //}
                        .background(
                            Color.White, shape = MaterialTheme.shapes.small
                        ).border(
                            width = 0.5.dp,
                            color = Color.LightGray,
                            shape = MaterialTheme.shapes.small
                        ).wrapContentHeight()
                        //.height(popupHeight)
                        .padding(vertical = 12.5.dp, horizontal = 15.dp)
                ) {
                    items(popupItem.second) { name ->
                        TextButton(onClick = {
                            Napier.v(name)
                        }) {
                            Text(
                                text = name,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Normal,
                                modifier = Modifier.padding(horizontal = 15.dp, vertical = 5.dp)
                            )
                        }


                    }

                }
                //}
            }
        }

    }


}


@Composable
fun SectionInit(backgroundPath: String, search: String, onSearchValueChange: (String) -> Unit) {
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
                        onClick = {},
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

sealed class MenuItem() {
    data object Home : MenuItem()
    data object Language : MenuItem()
    data object Login : MenuItem()
    data object Join : MenuItem()
    data object Search : MenuItem()
}


@OptIn(ExperimentalResourceApi::class)
@Composable
fun Header(
    modifier: Modifier = Modifier,
    onExpandMenuClick: (IntOffset, subMenu: Pair<String, List<String>>) -> Unit,
    onMenuClick: (MenuItem) -> Unit
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {

        if (LocalWindowSizeClass.current.widthSizeClass == WindowWidthSizeClass.Expanded) {
            Image(
                painter = org.jetbrains.compose.resources.painterResource("logo.xml"),
                contentDescription = null,
                modifier = Modifier.height(18.dp).clickable {
                    // Not necessary
                    // onMenuClick(MenuItem.Home)
                },
                //contentScale = ContentScale.FillWidth,
            )
            Spacer(Modifier.size(12.5.dp))

            HeaderTitleItem(text = menuMovies.first) {
                onExpandMenuClick(it, menuMovies)
            }

            HeaderTitleItem(text = menuTVShows.first) {
                onExpandMenuClick(it, menuTVShows)
            }

            HeaderTitleItem(text = menuPeople.first) {
                onExpandMenuClick(it, menuPeople)
            }

            HeaderTitleItem(text = menuMore.first) {
                onExpandMenuClick(it, menuMore)
            }

            Spacer(Modifier.weight(1f))

            var offsetAdd by remember { mutableStateOf(IntOffset.Zero) }
            var heightAddPx by remember {
                mutableStateOf(0)
            }

            IconButton(onClick = {
                onExpandMenuClick(
                    offsetAdd.copy(y = (offsetAdd.y + heightAddPx * 1.5).toInt()), menuAdd
                )
            }, modifier = Modifier.size(24.dp).onGloballyPositioned { coordinates ->
                val positionInParent: Offset = coordinates.positionInParent()
                heightAddPx = coordinates.size.height
                offsetAdd = IntOffset(positionInParent.x.toInt(), positionInParent.y.toInt())
            }) {
                Icon(
                    org.jetbrains.compose.resources.painterResource("ic_add.xml"),
                    contentDescription = "add",
                    tint = Color.White
                )
            }
            Spacer(Modifier.width(12.dp))


            HeaderTitleItem(
                text = "EN", modifier = Modifier.border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.onPrimary,
                    shape = MaterialTheme.shapes.medium
                ).padding(5.dp)
            ) {
                onMenuClick(MenuItem.Language)
            }
            HeaderTitleItem(text = "Login") {
                onMenuClick(MenuItem.Login)

            }

            HeaderTitleItem(text = "Join TMDB") {
                onMenuClick(MenuItem.Join)

            }

            IconButton(onClick = {
                onMenuClick(MenuItem.Search)
            }, modifier = Modifier.size(24.dp)) {
                Icon(
                    org.jetbrains.compose.resources.painterResource("ic_search.xml"),
                    contentDescription = "search",
                    tint = MaterialTheme.colorScheme.secondary
                )
            }
            Spacer(Modifier.size(12.5.dp))
        } else {
            IconButton(onClick = {
                onMenuClick(MenuItem.Home)
            }, modifier = Modifier.size(24.dp)) {
                Icon(
                    imageVector = Icons.Filled.Menu,
                    contentDescription = null,
                    tint = Color.White
                )
            }
            Spacer(Modifier.weight(1f))
            Image(
                painter = org.jetbrains.compose.resources.painterResource("logo.xml"),
                contentDescription = null,
                modifier = Modifier.height(15.dp),
                //contentScale = ContentScale.FillWidth,
            )
            Spacer(Modifier.weight(1f))
            IconButton(onClick = {
                onMenuClick(MenuItem.Login)
            }, modifier = Modifier.size(24.dp)) {
                Icon(
                    imageVector = Icons.Filled.Person,
                    contentDescription = "person",
                    tint = Color.White
                )
            }
            Spacer(Modifier.size(12.5.dp))
            IconButton(onClick = {
                onMenuClick(MenuItem.Search)
            }, modifier = Modifier.size(24.dp)) {
                Icon(
                    org.jetbrains.compose.resources.painterResource("ic_search.xml"),
                    contentDescription = "search",
                    tint = MaterialTheme.colorScheme.secondary
                )
            }
            Spacer(Modifier.size(12.5.dp))
        }


    }
}


@Composable
fun HeaderTitleItem(modifier: Modifier = Modifier, text: String, onClick: (IntOffset) -> Unit) {

    var offset by remember { mutableStateOf(IntOffset.Zero) }
    var columnHeightPx by remember {
        mutableStateOf(0)
    }
    Text(modifier = modifier.clickable {
        onClick(offset.copy(y = (offset.y + columnHeightPx * 1.5).toInt()))
    }.onGloballyPositioned { coordinates ->
        val positionInParent: Offset = coordinates.positionInParent()
        columnHeightPx = coordinates.size.height
        offset = IntOffset(positionInParent.x.toInt(), positionInParent.y.toInt())

    }.padding(horizontal = 7.5.dp, vertical = 5.dp),
        text = text,
        style = MaterialTheme.typography.titleMedium,
        color = MaterialTheme.colorScheme.onPrimary
    )
}


@Composable
internal fun App1() = AppTheme {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisibility by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxSize().windowInsetsPadding(WindowInsets.safeDrawing)) {

        Row(
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Login",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(16.dp)
            )

            Spacer(modifier = Modifier.weight(1.0f))

            var isDark by LocalThemeIsDark.current
            IconButton(onClick = { isDark = !isDark }) {
                Icon(
                    modifier = Modifier.padding(8.dp).size(20.dp),
                    imageVector = if (isDark) Icons.Default.LightMode else Icons.Default.DarkMode,
                    contentDescription = null
                )
            }
        }

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth().padding(16.dp)
        )

        OutlinedTextField(value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            singleLine = true,
            visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password
            ),
            trailingIcon = {
                IconButton(onClick = { passwordVisibility = !passwordVisibility }) {
                    val imageVector =
                        if (passwordVisibility) Icons.Default.Close else Icons.Default.Edit
                    Icon(
                        imageVector,
                        contentDescription = if (passwordVisibility) "Hide password" else "Show password"
                    )
                }
            })

        Button(
            onClick = { /* Handle login logic here */ },
            modifier = Modifier.fillMaxWidth().padding(16.dp)
        ) {
            Text("Login")
        }

        TextButton(
            onClick = { openUrl("https://github.com/terrakok") },
            modifier = Modifier.fillMaxWidth().padding(16.dp)
        ) {
            Text("Open github")
        }
    }
}






