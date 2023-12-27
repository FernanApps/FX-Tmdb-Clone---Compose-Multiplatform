package pe.fernan.kmp.tmdb.ui.details

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.outlined.Bookmark
import androidx.compose.material.icons.outlined.HeartBroken
import androidx.compose.material.icons.outlined.List
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Card
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.palette.graphics.Palette
import com.kmpalette.color
import com.kmpalette.loader.rememberPainterLoader
import com.kmpalette.rememberPaletteState
import com.seiko.imageloader.rememberImagePainter
import io.github.aakira.napier.Napier
import pe.fernan.kmp.tmdb.domain.model.MediaType
import pe.fernan.kmp.tmdb.domain.model.Result
import pe.fernan.kmp.tmdb.paddingInternal
import pe.fernan.kmp.tmdb.theme.LocalWindowSizeWidth
import pe.fernan.kmp.tmdb.theme.WindowSize
import pe.fernan.kmp.tmdb.titleTextStyle3
import pe.fernan.kmp.tmdb.ui.components.CircularProgressbar1
import pe.fernan.kmp.tmdb.ui.components.cardPosterHeight
import pe.fernan.kmp.tmdb.ui.components.cardPosterWidth
import pe.fernan.kmp.tmdb.ui.components.placeholderPainter
import pe.fernan.kmp.tmdb.ui.ext.dpToPx
import pe.fernan.kmp.tmdb.ui.home.HomeViewModel
import pe.fernan.kmp.tmdb.utils.Constant
import pe.fernan.kmp.tmdb.utils.getDate
import pe.fernan.kmp.tmdb.utils.toJson

interface DetailScreenClick {
    fun onTrailerClick(url: String)
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun DetailScreen(data: Result, viewModel: HomeViewModel, events: DetailScreenClick) {
    val detailsState by viewModel.detailsState.collectAsState()
    var youtubeUrl: String? by remember { mutableStateOf(null) }

    var isViewModelCalled by remember { mutableStateOf(false) }
    if (!isViewModelCalled) {
        Napier.d { "DetailsScreen data ::: \n${data.toJson()}" }
        viewModel.getResult(data.mediaType!!, data.id!!)
        isViewModelCalled = true
    }


    LaunchedEffect(detailsState) {
        youtubeUrl = if (detailsState.mediaType == MediaType.TV) {
            val videoResult = detailsState.tvSeries
                ?.videos?.results?.filter { it?.site == "YouTube" }

            val typesAndKeys = videoResult?.mapNotNull {
                it?.type ?: return@mapNotNull null
                it?.key ?: return@mapNotNull null
                it.type to it.key
            }?.toMap() ?: emptyMap()

            val keyVideoYoutube = if(typesAndKeys.containsKey("Trailer")){
                typesAndKeys["Trailer"]
            } else {
                typesAndKeys["Teaser"]
            }
            keyVideoYoutube?.let { Constant.getYoutubeEmbedUrl(videoKey = it) }

        } else {
            val videoResult = detailsState.movie
                ?.videos?.results?.filter { it?.site == "YouTube" }

            val typesAndKeys = videoResult?.mapNotNull {
                it?.type ?: return@mapNotNull null
                it?.key ?: return@mapNotNull null
                it.type to it.key
            }?.toMap() ?: emptyMap()

            val keyVideoYoutube = if(typesAndKeys.containsKey("Trailer")){
                typesAndKeys["Trailer"]
            } else {
                typesAndKeys["Teaser"]
            }
            keyVideoYoutube?.let { Constant.getYoutubeEmbedUrl(videoKey = it) }
        }
    }




    Box(modifier = Modifier.fillMaxSize()) {

        val painterBackground =
            rememberImagePainter(Constant.TMDB_IMAGE_BASE_PATH_CARD_HORIZONTAL_LARGE + data.backdropPath)

        val cardPosterWidth = when (LocalWindowSizeWidth.current) {
            WindowSize.Compact -> cardPosterWidth * 0.75f
            WindowSize.Medium -> cardPosterWidth
            WindowSize.Expanded -> cardPosterWidth * 1.25f
        }
        val cardPosterHeight = when (LocalWindowSizeWidth.current) {
            WindowSize.Compact -> cardPosterHeight * 0.95f
            WindowSize.Medium -> cardPosterHeight * 1.25f
            WindowSize.Expanded -> cardPosterHeight * 1.5f
        }

        val painterPoster = rememberImagePainter(
            Constant.TMDB_IMAGE_BASE_PATH_CARD + data.posterPath,
            placeholderPainter = {
                placeholderPainter(
                    width = cardPosterWidth.dpToPx(),
                    height = cardPosterHeight.dpToPx()
                )
            })

        val loader = rememberPainterLoader()
        val paletteState = rememberPaletteState(loader = loader)
        LaunchedEffect(painterPoster) {
            paletteState.generate(painterPoster)
        }
        var currentSwatch: Palette.Swatch? by remember { mutableStateOf(null) }
        val primaryColor = MaterialTheme.colorScheme.primary
        var currentColor: Color by remember { mutableStateOf(primaryColor) }

        LaunchedEffect(paletteState.palette) {
            currentSwatch = paletteState.palette?.dominantSwatch
            currentColor = currentSwatch?.color ?: primaryColor
        }

        val textColor = if (currentColor.luminance() < 0.5f) Color.White else Color.Black

        Box(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = painterBackground,
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop,
            )
        }

        Row(
            modifier = Modifier.fillMaxSize().background(color = currentColor.copy(alpha = 0.9f))
                .padding(paddingInternal)
        ) {
            Card(
                modifier = Modifier.width(cardPosterWidth).height(cardPosterHeight)
            ) {

                Image(
                    painter = painterPoster,
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop,
                )
            }
            Spacer(modifier = Modifier.size(paddingInternal))
            Column(modifier = Modifier.weight(1f)) {
                Spacer(modifier = Modifier.size(paddingInternal))


                Text(
                    text = buildAnnotatedString {
                        withStyle(
                            titleTextStyle3.copy(fontWeight = FontWeight.SemiBold).toSpanStyle()
                        ) {
                            append(data.title ?: data.name ?: "")
                        }
                        withStyle(
                            titleTextStyle3.copy(fontWeight = FontWeight.Light).toSpanStyle()
                        ) {
                            val year = data.date?.let { getDate(it)?.first }
                            append(if (year != null) " ($year)" else "")
                        }
                    },
                    color = textColor
                )
                Spacer(modifier = Modifier.size(2.5.dp))
                FlowRow(verticalArrangement = Arrangement.Center) {
                    Text(
                        // PG-13
                        text = "PG-21",
                        color = Color.LightGray,
                        modifier = Modifier.border(
                            width = 0.5.dp,
                            color = Color.LightGray,
                            shape = MaterialTheme.shapes.extraSmall
                        ).padding(horizontal = 10.dp)
                    )
                    Spacer(modifier = Modifier.size(5.dp))
                    Text(
                        text = "${
                            data.date?.replace(
                                "-",
                                "/"
                            ) ?: ""
                        } (US) • Action, Adventure, Fantasy • 2h 4m",
                        fontWeight = FontWeight.Light,
                        color = textColor
                    )
                }
                Spacer(modifier = Modifier.size(15.dp))
                FlowRow(verticalArrangement = Arrangement.Center) {
                    val progress = (data.voteAverage?.times(10f))?.toFloat() ?: 0f
                    val colorProgress = when (progress) {
                        in 1f..70f -> Color(0xFFD2D531)
                        in 71f..100f -> Color(0xFF21D07A)
                        else -> Color.Transparent
                    }

                    CircularProgressbar1(
                        size = 45.dp,
                        backgroundColor = colorScheme.primary,
                        backgroundInternalColor = colorScheme.primary,
                        foregroundIndicatorColor = colorProgress,
                        indicatorThickness = 3.5.dp,
                        backgroundInternalStroke = 3.5.dp,
                        textStyle = TextStyle(
                            fontSize = 12.sp,
                            color = Color.White,
                            fontWeight = FontWeight.SemiBold
                        ),
                        initialValue = progress
                    )
                    Spacer(modifier = Modifier.size(5.dp))
                    Text(
                        text = "User\nScore",
                        fontWeight = FontWeight.SemiBold,
                        color = textColor,
                        lineHeight = 10.sp,
                        fontSize = 12.sp,
                        modifier = Modifier.align(Alignment.CenterVertically)
                    )
                    Spacer(modifier = Modifier.size(10.5.dp))

                    FilledIconButton(onClick = {

                    }) {
                        Icon(Icons.Outlined.List, contentDescription = "Custom List")
                    }
                    FilledIconButton(onClick = {

                    }) {
                        Icon(Icons.Outlined.HeartBroken, contentDescription = "Favorite List")
                    }
                    FilledIconButton(onClick = {

                    }) {
                        Icon(Icons.Outlined.Bookmark, contentDescription = "Mark List")
                    }
                    FilledIconButton(onClick = {

                    }) {
                        Icon(Icons.Outlined.Star, contentDescription = "Mark List")
                    }
                    Spacer(modifier = Modifier.size(5.dp))

                    if (youtubeUrl != null && youtubeUrl?.isNotEmpty() == true) {

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.align(Alignment.CenterVertically)
                                .clickable {
                                    events.onTrailerClick(youtubeUrl!!)
                                }) {
                            Icon(
                                imageVector = Icons.Default.PlayArrow,
                                "Play trailer",
                                tint = textColor
                            )
                            Spacer(modifier = Modifier.size(5.dp))
                            Text(
                                text = "Play Trailer",
                                fontWeight = FontWeight.SemiBold,
                                color = textColor,
                                maxLines = 4
                            )
                        }
                    }

                }
                Spacer(modifier = Modifier.size(15.dp))
                Text(
                    text = "The tide is turning.\n",
                    fontWeight = FontWeight.Light,
                    color = textColor,
                    fontStyle = FontStyle.Italic
                )
                Spacer(modifier = Modifier.size(5.dp))
                Text(
                    text = "Overview",
                    fontWeight = FontWeight.SemiBold,
                    color = textColor,
                    fontSize = 14.sp.times(1.2f),
                    //modifier = Modifier.scale(1.2f)
                )
                Text(
                    text = data.overview ?: "",
                    fontWeight = FontWeight.Light,
                    color = textColor,
                    maxLines = 4
                )


            }
        }


    }
}

/*

AnimatedDynamicMaterialTheme(
            seedColor = currentSwatch?.color ?: Color.Blue,
            useDarkTheme = false,
            //style = PaletteStyle.Vibrant
        ) {
            Column {
                colorSchemePairs().forEach { (name, colors) ->
                    val (color, onColor) = colors

                    Row(modifier = Modifier.fillMaxWidth(0.5f)) {
                        ColorBox(text = name, color = color, modifier = Modifier.weight(1f))
                        ColorBox(text = "On$name", color = onColor, modifier = Modifier.weight(1f))
                    }
                }
            }
        }
 */

@Composable
fun ColorBox(
    text: String,
    color: Color,
    modifier: Modifier = Modifier,
) {
    val textColor = if (color.luminance() < 0.5f) Color.White else Color.Black
    Box(
        modifier = modifier
            .background(color)
    ) {
        Text(
            text = text,
            color = animateColorAsState(targetValue = textColor).value,
            modifier = Modifier.padding(8.dp),
        )
    }
}

@Composable
fun colorSchemePairs() = listOf(
    "Primary" to (colorScheme.primary to colorScheme.onPrimary),
    "PrimaryContainer" to (colorScheme.primaryContainer to colorScheme.onPrimaryContainer),
    "Secondary" to (colorScheme.secondary to colorScheme.onSecondary),
    "SecondaryContainer" to (colorScheme.secondaryContainer to colorScheme.onSecondaryContainer),
    "Tertiary" to (colorScheme.tertiary to colorScheme.onTertiary),
    "TertiaryContainer" to (colorScheme.tertiaryContainer to colorScheme.onTertiaryContainer),
    "Error" to (colorScheme.error to colorScheme.onError),
    "ErrorContainer" to (colorScheme.errorContainer to colorScheme.onErrorContainer),
    "Background" to (colorScheme.background to colorScheme.onBackground),
    "Surface" to (colorScheme.surface to colorScheme.onSurface),
    "SurfaceVariant" to (colorScheme.surfaceVariant to colorScheme.onSurfaceVariant),
)