package pe.fernan.kmp.tmdb

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.resource
import pe.fernan.kmp.tmdb.di.AppModule
import pe.fernan.kmp.tmdb.domain.model.Result
import pe.fernan.kmp.tmdb.theme.AppTheme
import pe.fernan.kmp.tmdb.theme.LocalThemeIsDark
import pe.fernan.kmp.tmdb.theme.LocalWindowSizeWidth
import pe.fernan.kmp.tmdb.theme.WindowSize
import pe.fernan.kmp.tmdb.ui.components.CardPosterHorizontal
import pe.fernan.kmp.tmdb.ui.ext.dpToPx
import pe.fernan.kmp.tmdb.ui.main.HeaderTitleItem
import pe.fernan.kmp.tmdb.ui.main.MainScreen
import pe.fernan.kmp.tmdb.ui.splash.SplashScreen
import pe.fernan.kmp.tmdb.utils.toModel


@OptIn(
    ExperimentalMaterial3Api::class,
    ExperimentalMaterial3WindowSizeClassApi::class,
    ExperimentalResourceApi::class
)
val paddingInternal: Dp
    @Composable get() = when (LocalWindowSizeWidth.current) {
        WindowSize.Expanded -> 40.dp
        WindowSize.Medium -> 25.dp
        else -> // WindowWidthSizeClass.Compact
            15.dp
    }

val titleTextStyle: TextStyle
    @Composable get() = when (LocalWindowSizeWidth.current) {
        WindowSize.Expanded -> MaterialTheme.typography.displayMedium
        WindowSize.Medium -> MaterialTheme.typography.headlineLarge
        else -> // WindowWidthSizeClass.Compact
            MaterialTheme.typography.headlineSmall
    }

val titleTextStyle2: TextStyle
    @Composable get() = when (LocalWindowSizeWidth.current) {
        WindowSize.Expanded -> MaterialTheme.typography.displaySmall
        WindowSize.Medium -> MaterialTheme.typography.headlineMedium
        else -> // WindowWidthSizeClass.Compact
            MaterialTheme.typography.headlineSmall
    }

val titleTextStyle3: TextStyle
    @Composable get() = when (LocalWindowSizeWidth.current) {
        WindowSize.Expanded -> MaterialTheme.typography.displaySmall
        WindowSize.Medium -> MaterialTheme.typography.headlineMedium
        else -> MaterialTheme.typography.bodyMedium
    }

val subTitleTextStyle: TextStyle
    @Composable get() = when (LocalWindowSizeWidth.current) {
        WindowSize.Expanded -> MaterialTheme.typography.headlineMedium
        WindowSize.Medium -> MaterialTheme.typography.titleMedium
        else -> // WindowWidthSizeClass.Compact
            MaterialTheme.typography.bodyMedium
    }

@Composable
internal fun MyApp() = AppTheme {
    Napier.base(DebugAntilog())
    val viewModel by lazy {
        AppModule.homeViewModel
    }

    var splash: Boolean by remember { mutableStateOf(true) }
    if (splash) {
        SplashScreen {
            if (splash) {
                splash = false
            }
        }
    } else {
        MainScreen(viewModel)
    }


}

@Composable
internal fun App() {
    MyApp()

}
/*
@Composable
internal fun LoadingResources(onLoading: @Composable () -> Unit) {
    var loadingResources: Boolean by remember { mutableStateOf(true) }

    if (loadingResources) {
        LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
    } else {
        onLoading()
    }

    LaunchedEffect(Unit) {
        loadMontserratFont()
        if (loadingResources) {
            loadingResources = false
        }
    }
}

 */




internal suspend fun loadFontResource(font: String) = loadResource("font/$font")
@OptIn(ExperimentalResourceApi::class)
@Composable
internal fun loadDrawableResource(drawable: String) = org.jetbrains.compose.resources.painterResource(
    "drawable/$drawable"
)


@OptIn(ExperimentalResourceApi::class)
internal suspend fun loadResource(resourcePath: String): ByteArray {
    return resource(resourcePath).readBytes()
}


fun ehPath(size: Size): Path {
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


@Composable
private fun DropDownSample() {
    var expanded by remember { mutableStateOf(false) }
    var touchPoint: Offset by remember { mutableStateOf(Offset.Zero) }
    val density = LocalDensity.current

    BoxWithConstraints(
        Modifier
            .fillMaxSize()
            .background(Color.Cyan)
            .pointerInput(Unit) {
                detectTapGestures {
                    Napier.d("TAG onCreate: ${it}")
                    touchPoint = it
                    expanded = true

                }

            }
    ) {
        val (xDp, yDp) = with(density) {
            (touchPoint.x.toDp()) to (touchPoint.y.toDp())
        }

        DropdownMenu(
            expanded = expanded,
            offset = DpOffset(xDp, -maxHeight + yDp),
            onDismissRequest = {
                expanded = false
            }
        ) {

            DropdownMenuItem(
                onClick = {
                    expanded = false
                },
                interactionSource = MutableInteractionSource(),
                text = {
                    Text("Copy")
                }
            )

            DropdownMenuItem(
                onClick = {
                    expanded = false
                },
                interactionSource = MutableInteractionSource(),
                text = {
                    Text("Get Balance")
                }
            )
        }
    }
}

@Composable
internal fun App11() = AppTheme {
    Napier.base(DebugAntilog())
    //DropDownSample()
    val list = listOf(
        "Streaming", "On TV", "For Recent", "In Theaters"
    )
    var text by remember { mutableStateOf(list.first()) }


    return@AppTheme

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


    var offset = Offset.Zero
    var dropDownExpanded by remember { mutableStateOf(false) }


    return@AppTheme


    Column(Modifier.fillMaxSize().background(Color.LightGray).padding(12.dp)) {

        HeaderTitleItem(text = "HeaderItem") {
            offset = Offset(it.x.toFloat(), it.y.toFloat())
            dropDownExpanded = !dropDownExpanded
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
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

        }
        DropdownMenu(
            expanded = dropDownExpanded,
            //offset = DpOffset(offset.x.dp, offset.y.dp),
            modifier = Modifier.background(
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
            },
            onDismissRequest = { dropDownExpanded = false }) {
            Text("Holla")
            return@DropdownMenu

            Column(
                modifier = Modifier
                    .fillMaxSize()
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


@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
internal fun App123() = AppTheme {
    Napier.base(DebugAntilog())

    val corner = 25.dp
    var expanded by remember { mutableStateOf(true) }

    val moviesList = listOf(
        "Streaming",
        "Popular",
        "On TV",
        "For Recent",
        "In Theaters",
    )
    var selectedMovie by remember { mutableStateOf(moviesList[0]) }


    val shape = RoundedCornerShape(
        topStart = corner,
        topEnd = corner,
        bottomStart = corner,
        bottomEnd = corner
    )


    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.TopCenter) {

        MaterialTheme(
            colorScheme = MaterialTheme.colorScheme.copy(surface = Color(0xFFe3f2fd)),
            shapes = MaterialTheme.shapes.copy(
                extraSmall = shape,
                small = shape,
                medium = shape,
                large = shape,
                extraLarge = shape
            )
        ) {
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = {
                    expanded = !expanded
                },
                modifier = Modifier.width(150.dp)
            ) {
                /*

                TextField(
                    textStyle = TextStyle(color = Color.Magenta, fontSize = 14.sp),
                    modifier = Modifier
                        .menuAnchor().background(
                            brush = Brush.horizontalGradient(
                                colors =
                                listOf(
                                    Color(0xFFBEFDCE),
                                    Color(0xFF20D5A9)
                                )
                            ),
                            shape = shapeMenu
                        ),
                    readOnly = true,
                    value = selectedMovie,
                    onValueChange = {},
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                    colors = ExposedDropdownMenuDefaults.textFieldColors(
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        focusedContainerColor = Color.Transparent,
                        disabledContainerColor = Color.Transparent,
                        errorContainerColor = Color.Transparent
                    ),
                )
                */
                Row(
                    modifier = Modifier
                        .menuAnchor()
                        .background(
                            brush = Brush.linearGradient(
                                colors =
                                listOf(
                                    Color(0xFFBEFDCE),
                                    Color(0xFF20D5A9)
                                )
                            ), shape = shape
                        )
                        .border(
                            width = 0.5.dp,
                            color = Color(0xFF1ED5A9),
                            shape = shape
                        )
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(text = selectedMovie, Modifier.width(100.dp))
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                }


                // menu
                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = {
                        expanded = false
                    },
                    modifier = Modifier.background(
                        brush = Brush.horizontalGradient(
                            colors =
                            listOf(
                                Color(0xFFBEFDCE),
                                Color(0xFF20D5A9)
                            )
                        )
                    ),
                ) {
                    // menu items
                    moviesList.forEach { selectionOption ->
                        DropdownMenuItem(
                            modifier = Modifier.height(30.dp),
                            text = {
                                Box(contentAlignment = Alignment.Center) {
                                    Text(selectionOption, fontSize = 10.sp)
                                }
                            },
                            onClick = {
                                selectedMovie = selectionOption
                                expanded = false
                            },
                            contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                        )
                    }
                }
            }

        }
    }


}

@Composable
internal fun App2() = AppTheme {
    Napier.base(DebugAntilog())

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

    val data = json.toModel<Result>()
    Box(
        modifier = Modifier.fillMaxSize().background(Color.White),
        contentAlignment = Alignment.Center
    ) {

        LazyColumn() {
            item {
                CardPosterHorizontal(data = data, onMouseover = {
                    Napier.i { "onMouseover ${data.title} = $it" }
                }) {

                }
            }
        }

    }

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

internal expect fun openUrl(url: String?)


