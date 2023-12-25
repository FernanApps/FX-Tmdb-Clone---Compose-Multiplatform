
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.HorizontalScrollbar
import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabPosition
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import androidx.compose.ui.window.singleWindowApplication
import pe.fernan.kmp.tmdb.App
import pe.fernan.kmp.tmdb.domain.model.Result
import pe.fernan.kmp.tmdb.theme.AppTheme
import pe.fernan.kmp.tmdb.ui.components.CardPosterHorizontal
import pe.fernan.kmp.tmdb.ui.components.CircularProgressbar1
import pe.fernan.kmp.tmdb.ui.components.MultiSelector
import pe.fernan.kmp.tmdb.ui.components.TextSwitch
import pe.fernan.kmp.tmdb.utils.toModel
import java.awt.Dimension

@Preview
@Composable
fun PreviewItemScreen() {
    AppTheme {

    }
}


@OptIn(ExperimentalMaterial3Api::class)
fun main() = application {
    Window(
        title = "FX-Tmdb - Compose Multiplatform",
        state = rememberWindowState(width = 800.dp, height = 600.dp),
        onCloseRequest = ::exitApplication,
    ) {
        App()
        //Napier.base(DebugAntilog())
        //PreviewItemScreen()
        "ASASAs".format("asas")
    }
}



fun main222() = application {
    Window(
        title = "FX-Tmdb - Compose Multiplatform",
        state = rememberWindowState(width = 800.dp, height = 600.dp),
        onCloseRequest = ::exitApplication,
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.Gray),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressbar1(
                size = 35.dp,
                indicatorThickness = 2.5.dp,
                backgroundInternalStroke = 1.5.dp,
                textStyle = TextStyle(
                    fontSize = 10.sp,
                    color = Color.White,
                    fontWeight = FontWeight.SemiBold
                ),
                initialValue = 30f
            )
        }
    }
}






fun main111111() = application {
    Window(
        title = "FX-Tmdb - Compose Multiplatform",
        state = rememberWindowState(width = 800.dp, height = 600.dp),
        onCloseRequest = ::exitApplication,
    ) {
        window.minimumSize = Dimension(350, 600)

        val json = """{
      "adult": false,
      "backdrop_path": "/8GnWDLn2AhnmkQ7hlQ9NJUYobSS.jpg",
      "id": 695721,
      "title": "Los juegos del hambre: Balada de pájaros cantores y serpientes",
      "original_language": "en",
      "original_title": "The Hunger Games: The Ballad of Songbirds & Snakes",
      "overview": "Ambientada en un Panem postapocalíptico, nos hace retroceder varias décadas antes del comienzo de las aventuras de Katniss Everdeen. El joven Coriolanus Snow será el mentor de Lucy Gray Baird, la niña seleccionada como tributo del empobrecido Distrito 12. La joven sorprenderá a todos al cantar en la ceremonia de inauguración de los Décimos Juegos del Hambre en los que Snow intentará aprovecharse de su talento y encanto para sobrevivir.",
      "poster_path": "/z9oNYBLNa6f4MyLIkihlDSi1hxe.jpg",
      "media_type": "movie",
      "genre_ids": [
        18,
        878,
        28
      ],
      "popularity": 810.099,
      "release_date": "2023-11-15",
      "video": false,
      "vote_average": 7.282,
      "vote_count": 835,
      "name" :  "",
      "original_name" : "",
      "first_air_date" : "",
      "origin_country" : [
        "ES"
      ]
    }"""

        AppTheme {
            val data = json.toModel<Result>()
            Box(
                modifier = Modifier.fillMaxSize().background(Color.White),
                contentAlignment = Alignment.Center
            ) {

                LazyColumn() {
                    item {
                        CardPosterHorizontal(data = data, onMouseover = {

                        }) {

                        }
                    }
                }

            }

        }

    }
}


fun main2222() = application {
    Window(
        title = "FX-Tmdb - Compose Multiplatform",
        state = rememberWindowState(width = 800.dp, height = 600.dp),
        onCloseRequest = ::exitApplication,
    ) {
        window.minimumSize = Dimension(350, 600)
        App()
    }
}








fun main2aa() = application {
    Window(
        title = "FX-Tmdb - Compose Multiplatform",
        state = rememberWindowState(width = 800.dp, height = 600.dp),
        onCloseRequest = ::exitApplication,
    ) {
        window.minimumSize = Dimension(350, 600)
        var expanded by remember { mutableStateOf(false) }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .wrapContentSize(Alignment.TopStart)
        ) {
            IconButton(onClick = { expanded = true }) {
                Icon(Icons.Default.MoreVert, contentDescription = "Localized description")
            }
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                DropdownMenuItem(
                    text = { Text("Edit") },
                    onClick = { /* Handle edit! */ },
                    leadingIcon = {
                        Icon(
                            Icons.Outlined.Edit,
                            contentDescription = null
                        )
                    })
                DropdownMenuItem(
                    text = { Text("Settings") },
                    onClick = { /* Handle settings! */ },
                    leadingIcon = {
                        Icon(
                            Icons.Outlined.Settings,
                            contentDescription = null
                        )
                    })
                Divider()
                DropdownMenuItem(
                    text = { Text("Send Feedback") },
                    onClick = { /* Handle send feedback! */ },
                    leadingIcon = {
                        Icon(
                            Icons.Outlined.Email,
                            contentDescription = null
                        )
                    },
                    trailingIcon = { Text("F11", textAlign = TextAlign.Center) })
            }
        }
    }
}

fun main21() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Scrollbars",
        state = rememberWindowState(width = 250.dp, height = 400.dp)
    ) {
        LazyScrollable()
    }
}

@Composable
fun LazyScrollable() {
    Box(
        modifier = Modifier.fillMaxSize()
            .background(color = Color(180, 180, 180))
            .padding(10.dp)
    ) {

        val state = rememberLazyListState()

        LazyColumn(Modifier.fillMaxSize().padding(end = 12.dp), state) {
            items(100) { x ->
                TextBox("Item #$x")
                Spacer(modifier = Modifier.height(5.dp))
            }
        }
        VerticalScrollbar(
            modifier = Modifier.align(Alignment.CenterEnd).fillMaxHeight(),
            adapter = rememberScrollbarAdapter(
                scrollState = state
            )
        )
    }
}

fun main22() = singleWindowApplication(
    title = "Scrollbars",
    state = WindowState(width = 250.dp, height = 400.dp)
) {
    Box(
        modifier = Modifier.fillMaxSize()
            .background(color = Color(180, 180, 180))
            .padding(10.dp)
    ) {
        val stateVertical = rememberScrollState(0)
        val stateHorizontal = rememberScrollState(0)

        Box(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(stateVertical)
                .padding(end = 12.dp, bottom = 12.dp)
                .horizontalScroll(stateHorizontal)
        ) {
            Column {
                for (item in 0..30) {
                    TextBox("Item #$item")
                    if (item < 30) {
                        Spacer(modifier = Modifier.height(5.dp))
                    }
                }
            }
        }
        VerticalScrollbar(
            modifier = Modifier.align(Alignment.CenterEnd)
                .fillMaxHeight(),
            adapter = rememberScrollbarAdapter(stateVertical)
        )
        HorizontalScrollbar(
            modifier = Modifier.align(Alignment.BottomStart)
                .fillMaxWidth()
                .padding(end = 12.dp),
            adapter = rememberScrollbarAdapter(stateHorizontal)
        )
    }
}

@Composable
fun TextBox(text: String = "Item") {
    Box(
        modifier = Modifier.height(32.dp)
            .width(400.dp)
            .background(color = Color(200, 0, 0, 20))
            .padding(start = 10.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Text(text = text)
    }
}


@OptIn(ExperimentalMaterial3Api::class)
fun main1() = application {
    Window(
        title = "FX-Tmdb - Compose Multiplatform",
        state = rememberWindowState(width = 800.dp, height = 600.dp),
        onCloseRequest = ::exitApplication,
    ) {
        window.minimumSize = Dimension(350, 600)
        //TextSwitchTest()
        //MultiSelectorTest()
        //CustomTabs()


        return@Window

    }
}


@Composable
fun MultiSelectorTest() {
    Surface(
        color = MaterialTheme.colorScheme.background,
    ) {
        val options1 = listOf("Lorem", "Ipsum", "Dolor")
        var selectedOption1 by remember {
            mutableStateOf(options1.first())
        }
        val options2 = listOf("Sit", "Amet", "Consectetur", "Elit", "Quis")
        var selectedOption2 by remember {
            mutableStateOf(options2.first())
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            MultiSelector(
                options = options1,
                selectedOption = selectedOption1,
                onOptionSelect = { option ->
                    selectedOption1 = option
                },
                modifier = Modifier
                    .padding(all = 16.dp)
                    .fillMaxWidth()
                    .height(56.dp)
            )

            MultiSelector(
                options = options2,
                selectedOption = selectedOption2,
                onOptionSelect = { option ->
                    selectedOption2 = option
                },
                modifier = Modifier
                    .padding(all = 16.dp)
                    .fillMaxWidth()
                    .height(56.dp)
            )
        }
    }
}

@Preview
@Composable
private fun TextSwitchTest() {
    val items = remember {
        listOf("Man", "Woman")
    }

    var selectedIndex by remember {
        mutableStateOf(0)
    }


    Column {
        TextSwitch(
            selectedIndex = selectedIndex,
            items = items,
            onSelectionChange = {
                selectedIndex = it
            }
        )
    }
}

@Composable
fun CustomTabs() {
    var selectedIndex by remember { mutableStateOf(0) }

    val list = listOf("Active", "Completed")

    TabRow(selectedTabIndex = selectedIndex,
        containerColor = Color(0xff1E76DA),
        modifier = Modifier
            .padding(vertical = 4.dp, horizontal = 8.dp)
            .clip(RoundedCornerShape(50))
            .padding(1.dp),
        indicator = { tabPositions: List<TabPosition> ->
            Box {}
        }
    ) {
        list.forEachIndexed { index, text ->
            val selected = selectedIndex == index
            Tab(
                modifier = if (selected) Modifier
                    .clip(RoundedCornerShape(50))
                    .background(
                        Color.White
                    )
                else Modifier
                    .clip(RoundedCornerShape(50))
                    .background(
                        Color(
                            0xff1E76DA
                        )
                    ),
                selected = selected,
                onClick = { selectedIndex = index },
                text = { Text(text = text, color = Color(0xff6FAAEE)) }
            )
        }
    }
}



