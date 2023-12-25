package pe.fernan.kmp.tmdb.ui.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import io.github.aakira.napier.Napier
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import moe.tlaster.precompose.PreComposeApp
import moe.tlaster.precompose.navigation.NavHost
import moe.tlaster.precompose.navigation.rememberNavigator
import org.jetbrains.compose.resources.ExperimentalResourceApi
import pe.fernan.kmp.tmdb.domain.model.MediaType
import pe.fernan.kmp.tmdb.domain.model.MovieListType
import pe.fernan.kmp.tmdb.domain.model.TVSeriesListType
import pe.fernan.kmp.tmdb.loadDrawableResource
import pe.fernan.kmp.tmdb.paddingInternal
import pe.fernan.kmp.tmdb.theme.LocalWindowSizeClass
import pe.fernan.kmp.tmdb.ui.details.DetailScreen
import pe.fernan.kmp.tmdb.ui.home.HomeScreen
import pe.fernan.kmp.tmdb.ui.home.HomeViewModel
import pe.fernan.kmp.tmdb.ui.items.ItemsScreen
import pe.fernan.kmp.tmdb.ui.navigation.KeyRoute
import pe.fernan.kmp.tmdb.ui.navigation.KeyRoutes
import pe.fernan.kmp.tmdb.ui.navigation.NavigateRoute
import pe.fernan.kmp.tmdb.ui.navigation.Screen
import pe.fernan.kmp.tmdb.utils.TimeExt.now


val itemMovie = KeyRoutes(
    main = KeyRoute(key = MediaType.MOVIE.value, "Movies"),
    routes = setOf(
        KeyRoute(MovieListType.NOW_PLAYING.value, "Now Playing"),
        KeyRoute(MovieListType.POPULAR.value, "Popular"),
        KeyRoute(MovieListType.TOP_RATED.value, "Top Rated"),
        KeyRoute(MovieListType.UPCOMING.value, "Upcoming"),
    )
)

val itemSeriesTV = KeyRoutes(
    main = KeyRoute(key = MediaType.TV.value, "TV Series"),
    routes = setOf(
        KeyRoute(TVSeriesListType.AIRING_TODAY.value, "Airing Today"),
        KeyRoute(TVSeriesListType.ON_THE_AIR.value, "On The Air"),
        KeyRoute(TVSeriesListType.POPULAR.value, "Popular"),
        KeyRoute(TVSeriesListType.TOP_RATED.value, "Top Rated"),
    )
)

val menuMovies = itemMovie
val menuTVShows = itemSeriesTV
val menuPeople = KeyRoutes(
    main = KeyRoute(key = "people", "People"), routes = setOf(KeyRoute("popular", "Popular People"))
)

val menuMore = KeyRoutes(
    main = KeyRoute(key = "more", "More"), routes = setOf(
        KeyRoute("discussions", "Discussions"),
        KeyRoute("Leaderboard", "Leaderboard"),
        KeyRoute("Support", "Support"),
        KeyRoute(
            "api", "Api"
        )
    )
)
val menuAdd = KeyRoutes(
    main = KeyRoute(key = "add", "Add"),
    routes = setOf(KeyRoute("login", "Can't find a movie or TV show?\nLogin to create it"))
)


private val menuList = listOf(
    menuMovies, menuTVShows, menuPeople, menuMore, menuAdd
)

private
val drawerItemList = menuList


@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun MainScreen(viewModel: HomeViewModel) {
    Napier.i("HomeScreen init ${Clock.System.now()}")


    val now = LocalDateTime.now()
    val dateNow = with(now) {
        "${dayOfMonth}/${monthNumber}/$year - $hour : $minute : $second "
    }
    var popupControl by remember { mutableStateOf(false) }
    var popupItem: KeyRoutes? by remember { mutableStateOf(null) }
    var popupOffset by remember { mutableStateOf(IntOffset.Zero) }
    var search by remember { mutableStateOf("") }


    val materialBlue700 = MaterialTheme.colorScheme.primary


    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()
    val drawerSelectedItem = remember { mutableStateOf(drawerItemList.first()) }

    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(
        rememberTopAppBarState()
    )

    PreComposeApp {
        val navigator = rememberNavigator()

        Scaffold(
            modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
            topBar = {

                TopAppBar(
                    scrollBehavior = scrollBehavior, title = {
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
                                    navigator.navigate(Screen.Home.route)
                                }

                                MenuItem.Menu -> {
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


            },
            content = {
                Box(
                    modifier = Modifier.fillMaxSize().padding(it)
                ) {

                    TmdbNavigationDrawer(drawerState = drawerState, navigationClose = {
                        coroutineScope.launch {
                            drawerState.close()
                        }
                    }, onNavigationItemSelected = { route ->
                        coroutineScope.launch {
                            drawerState.close()
                        }
                        navigator.navigate(Screen.Items.passObject(route))
                    }) {
                        // Content
                        NavHost(
                            navigator = navigator, initialRoute = Screen.Home.route
                        ) {
                            scene(route = Screen.Home.route) {
                                HomeScreen(viewModel, onClickSearch = {

                                }, onItemClick = { result ->
                                    navigator.navigate(Screen.Details.passObject(result))

                                })
                            }
                            scene(route = Screen.Details.route) { backStackEntry ->
                                val result = Screen.Details.getObject(backStackEntry.pathMap)
                                DetailScreen(result)
                            }

                            scene(route = Screen.Items.route) { backStackEntry ->
                                val navigateRoute = Screen.Items.getObject(backStackEntry.pathMap)
                                ItemsScreen(navigateRoute, viewModel, onItemSelected = { result ->
                                    navigator.navigate(Screen.Details.passObject(result))

                                })
                            }


                        }

                    }
                }


            },
            bottomBar = {
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


        if (popupControl && popupItem != null) {
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
                        items(popupItem!!.routes.toList()) { keyRoute ->
                            TextButton(onClick = {
                                Napier.v("keyRoute $keyRoute")
                                popupControl = false
                                navigator.navigate(
                                    Screen.Items.passObject(
                                        NavigateRoute(
                                            parentKey = popupItem!!.main, routeKey = keyRoute
                                        )
                                    )
                                )
                            }) {
                                Text(
                                    text = keyRoute.title,
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Normal,
                                    modifier = Modifier.padding(
                                        horizontal = 15.dp,
                                        vertical = 5.dp
                                    )
                                )
                            }


                        }

                    }
                    //}
                }
            }

        }

    }


}

@Composable
fun TmdbNavigationDrawer(
    drawerState: DrawerState,
    navigationClose: () -> Unit,
    onNavigationItemSelected: (key: NavigateRoute) -> Unit,
    content: @Composable () -> Unit
) {
    ModalNavigationDrawer(
        drawerState = drawerState, drawerContent = {
            ModalDrawerSheet(
                drawerContainerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.97f)
            ) {
                Column(
                    modifier = Modifier.fillMaxHeight().verticalScroll(rememberScrollState())
                        .padding(paddingInternal / 2)
                ) {
                    drawerItemList.forEachIndexed { index, item ->
                        var isExpandInternal by remember(key1 = "$index-$item") {
                            mutableStateOf(false)
                        }
                        val main = item.main
                        val routes = item.routes
                        Column(
                            modifier = Modifier
                        ) {
                            Text(
                                text = main.title,
                                style = MaterialTheme.typography.titleMedium,
                                color = MaterialTheme.colorScheme.onPrimary,
                                fontWeight = FontWeight.SemiBold,
                                modifier = Modifier.clickable {
                                    if (routes.isNotEmpty()) {
                                        isExpandInternal = !isExpandInternal
                                    } else {
                                        navigationClose()

                                    }
                                }.padding(vertical = 8.dp, horizontal = 16.dp)
                            )
                            if (isExpandInternal) {
                                Column {
                                    routes.forEachIndexed { index, routeKey ->
                                        Text(
                                            text = routeKey.title,
                                            style = MaterialTheme.typography.titleSmall,
                                            color = MaterialTheme.colorScheme.onPrimary,
                                            fontWeight = FontWeight.Normal,
                                            modifier = Modifier.clickable {
                                                onNavigationItemSelected(
                                                    NavigateRoute(
                                                        parentKey = main,
                                                        routeKey = routeKey
                                                    )
                                                )
                                            }.padding(
                                                vertical = 5.dp, horizontal = 16.dp
                                            )

                                        )
                                    }
                                }
                                Spacer(Modifier.size(8.dp))
                            }
                        }


                    }

                }
            }
        }, content = content
    )
}


sealed class MenuItem() {
    data object Home : MenuItem()
    data object Menu : MenuItem()
    data object Language : MenuItem()
    data object Login : MenuItem()
    data object Join : MenuItem()
    data object Search : MenuItem()
}


@OptIn(ExperimentalResourceApi::class)
@Composable
fun Header(
    modifier: Modifier = Modifier,
    onExpandMenuClick: (IntOffset, KeyRoutes) -> Unit,
    onMenuClick: (MenuItem) -> Unit
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {

        if (LocalWindowSizeClass.current.widthSizeClass == WindowWidthSizeClass.Expanded) {
            Image(
                painter = loadDrawableResource("logo.xml"),
                contentDescription = null,
                modifier = Modifier.height(18.dp).clickable {
                    onMenuClick(MenuItem.Home)
                },
                //contentScale = ContentScale.FillWidth,
            )
            Spacer(Modifier.size(12.5.dp))

            HeaderTitleItem(text = menuMovies.main.title) {
                onExpandMenuClick(it, menuMovies)
            }

            HeaderTitleItem(text = menuTVShows.main.title) {
                onExpandMenuClick(it, menuTVShows)
            }

            HeaderTitleItem(text = menuPeople.main.title) {
                onExpandMenuClick(it, menuPeople)
            }

            HeaderTitleItem(text = menuMore.main.title) {
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
                    loadDrawableResource("ic_add.xml"),
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
                    loadDrawableResource("ic_search.xml"),
                    contentDescription = "search",
                    tint = MaterialTheme.colorScheme.secondary
                )
            }
            Spacer(Modifier.size(12.5.dp))
        } else {
            IconButton(onClick = {
                onMenuClick(MenuItem.Menu)
            }, modifier = Modifier.size(24.dp)) {
                Icon(
                    imageVector = Icons.Filled.Menu,
                    contentDescription = null,
                    tint = Color.White
                )
            }
            Spacer(Modifier.weight(1f))
            Image(
                painter = loadDrawableResource("logo.xml"),
                contentDescription = null,
                modifier = Modifier.height(15.dp).clickable {
                    onMenuClick(MenuItem.Home)
                },
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
                    loadDrawableResource("ic_search.xml"),
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







