package pe.fernan.kmp.tmdb.ui.search

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import pe.fernan.kmp.tmdb.domain.model.MediaType
import pe.fernan.kmp.tmdb.domain.model.Result
import pe.fernan.kmp.tmdb.paddingInternal
import pe.fernan.kmp.tmdb.theme.LocalWindowSizeWidth
import pe.fernan.kmp.tmdb.theme.WindowSize
import pe.fernan.kmp.tmdb.ui.common.VerticalScrollbarCommon
import pe.fernan.kmp.tmdb.ui.common.rememberScrollbarAdapterCommon
import pe.fernan.kmp.tmdb.ui.components.CardPoster2
import pe.fernan.kmp.tmdb.ui.components.CustomTextField
import pe.fernan.kmp.tmdb.ui.home.HomeViewModel
import kotlin.random.Random


private val paddingHorizontal = 16.dp
private val paddingVertical = 8.dp

private val colorText = Color(0xFF212529)
private val cardWidth = 225.dp
private val cardElevation = 1.dp
private val cardElevationDefault
    @Composable get() = CardDefaults.cardElevation(
        cardElevation,
        cardElevation,
        cardElevation,
        cardElevation,
        cardElevation,
        cardElevation
    )

data class Search(val key: MediaType, val title: String, val size: Int = 0) {
    override fun toString(): String {
        return "Search(key=$key, title='$title', size=$size)"
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    query: String,
    viewModel: HomeViewModel,
    onItemSelected: (Result) -> Unit
) {

    var querySearch by remember { mutableStateOf(query) }

    val searchState by viewModel.searchState.collectAsState()

    var currentMediaType by remember { mutableStateOf(MediaType.MOVIE) }

    var isViewModelCalled by remember { mutableStateOf(false) }
    if (!isViewModelCalled) {
        viewModel.getSearchList(querySearch, currentMediaType)
        isViewModelCalled = true
    }

    var searchKeys by remember {
        mutableStateOf(
            mapOf<MediaType, Search>(
                MediaType.MOVIE to Search(MediaType.MOVIE, "Movies"),
                MediaType.TV to Search(MediaType.TV, "TV Series")
            )
        )
    }

    LaunchedEffect(searchState) {
        if (searchState.itemsList != null && searchState.itemsList?.isNotEmpty() == true) {
            val _searchKeys = searchKeys.toMutableMap()
            _searchKeys[currentMediaType] =
                _searchKeys[currentMediaType]!!.copy(size = searchState.itemsList!!.size)
            searchKeys = _searchKeys
        }
    }

    @Composable
    fun SearchFilterContent(modifier: Modifier = Modifier.width(cardWidth)) {

        Card(
            elevation = cardElevationDefault,
            colors = CardDefaults.cardColors(containerColor = Color.White),
            modifier = modifier
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {


                Text(
                    text = "Search Results",
                    color = Color.White,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.fillMaxWidth(1f)
                        .background(color = MaterialTheme.colorScheme.secondary).padding(
                            horizontal = paddingHorizontal,
                            vertical = paddingVertical + 5.dp
                        ).padding(horizontal = paddingHorizontal, vertical = paddingVertical)
                )

                Spacer(modifier = Modifier.height(10.dp))
                if (searchState.itemsList != null) {
                    // Next Fix please :(
                    searchKeys.values.toList().forEachIndexed { index, search ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth().clickable {
                                viewModel.getSearchList(query = querySearch, search.key)

                            }.padding(horizontal = paddingHorizontal, vertical = paddingVertical)
                        ) {

                            Text(
                                search.title,
                                modifier.fillMaxWidth(),
                                fontWeight = FontWeight.SemiBold
                            )

                            /*
                            if (search.size > 0) {
                                Text(
                                    text = search.size.toString(),
                                    modifier = Modifier.background(
                                        color = Color.LightGray,
                                        MaterialTheme.shapes.extraSmall
                                    ),
                                    fontWeight = FontWeight.Light
                                )

                            }

                             */

                        }


                    }

                }
                Spacer(modifier = Modifier.height(10.dp))


            }


        }

    }

    @Composable
    fun ItemsContent(
        modifier: Modifier = Modifier, showScrollBar: Boolean = true,
        columns: Int = 2,
        itemHeaderContent: (@Composable () -> Unit)? = null,
    ) {
        val state = rememberLazyGridState()
        val paddingInternal = paddingInternal

        Row {
            LazyVerticalGrid(
                state = state,
                columns = GridCells.Fixed(columns),
                modifier = modifier.weight(1f)
            ) {

                if (itemHeaderContent != null) {
                    item(span = { GridItemSpan(columns) }) {
                        itemHeaderContent()
                    }
                }

                items(
                    items = searchState.itemsList ?: (0..25).toList()
                        .mapIndexed { index, it -> Result(id = index) },
                    key = { "${it.id ?: Random.nextInt()}-${it.mediaType}" }
                ) { data ->

                    Column {
                        Row(horizontalArrangement = Arrangement.Center) {
                            Card(
                                elevation = cardElevationDefault,
                                colors = CardDefaults.cardColors(containerColor = Color.White),
                                modifier = modifier.weight(1f)
                            ) {
                                CardPoster2(
                                    data = data,
                                    loading = searchState.itemsList == null
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
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Outlined.Search, null, modifier = Modifier.size(15.dp))
            CustomTextField(
                modifier = Modifier.weight(1f),
                backgroundColor = Color.Transparent,
                shape = RoundedCornerShape(0.dp),
                value = querySearch,
                onValueChange = {
                    querySearch = it
                },
                label = {
                    Text(
                        "Search for a movie, tv show, person......", color = Color.LightGray,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                trailingIcon = {
                    Text("✖", modifier = Modifier.clip(CircleShape).clickable {
                        querySearch = ""
                    })
                })
        }
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

                ItemsContent(columns = 1) {
                    Column(
                        //state = state,
                        modifier = Modifier.fillMaxSize().weight(1f)
                    ) {
                        SearchFilterContent(Modifier.fillMaxWidth())
                        Spacer(modifier = Modifier.size(paddingInternal))
                    }
                }
            }
        } else {
            Row {
                Column {
                    SearchFilterContent()
                }
                Spacer(modifier = Modifier.size(paddingInternal))
                ItemsContent(columns = if (LocalWindowSizeWidth.current == WindowSize.Medium) 1 else 2)
            }
        }

    }


}




