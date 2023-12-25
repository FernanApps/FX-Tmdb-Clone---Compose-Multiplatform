package pe.fernan.kmp.tmdb.ui.details

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
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
import pe.fernan.kmp.tmdb.domain.model.Result
import pe.fernan.kmp.tmdb.paddingInternal
import pe.fernan.kmp.tmdb.titleTextStyle2
import pe.fernan.kmp.tmdb.ui.components.CircularProgressbar1
import pe.fernan.kmp.tmdb.ui.components.cardPosterHeight
import pe.fernan.kmp.tmdb.ui.components.cardPosterWidth
import pe.fernan.kmp.tmdb.ui.components.placeholderPainter
import pe.fernan.kmp.tmdb.ui.ext.dpToPx
import pe.fernan.kmp.tmdb.utils.Constant
import pe.fernan.kmp.tmdb.utils.getDate

@Composable
fun DetailScreen(data: Result) {
    Box(modifier = Modifier.fillMaxSize()) {

        val painterBackground =
            rememberImagePainter(Constant.IMAGE_BASE_PATH_CARD_HORIZONTAL_LARGE + data.backdropPath)

        val painterPoster = rememberImagePainter(
            Constant.IMAGE_BASE_PATH_CARD + data.posterPath,
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
                modifier = Modifier.width(cardPosterWidth * 1.25f).height(cardPosterHeight * 1.5f)
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
                            titleTextStyle2.copy(fontWeight = FontWeight.SemiBold).toSpanStyle()
                        ) {
                            append(data.title ?: data.name ?: "")
                        }
                        withStyle(
                            titleTextStyle2.copy(fontWeight = FontWeight.Light).toSpanStyle()
                        ) {
                            val year = data.date?.let { getDate(it)?.first }
                            append(if (year != null) " ($year)" else "")
                        }
                    },
                    color = textColor
                )
                Spacer(modifier = Modifier.size(2.5.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        // PG-13
                        text = "",
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
                Row(verticalAlignment = Alignment.CenterVertically) {
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
                        fontSize = 12.sp
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