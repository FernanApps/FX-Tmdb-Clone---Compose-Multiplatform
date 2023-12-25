package pe.fernan.kmp.tmdb.ui.components

import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.seiko.imageloader.rememberImagePainter
import pe.fernan.kmp.tmdb.domain.model.Result
import pe.fernan.kmp.tmdb.ui.ext.dpToPx
import pe.fernan.kmp.tmdb.utils.Constant


@OptIn(ExperimentalFoundationApi::class)

@Composable
fun LazyItemScope.CardPoster(
    modifier: Modifier = Modifier,
    data: Result,
    loading: Boolean = false,
    onClick: (Result) -> Unit
) = pe.fernan.kmp.tmdb.ui.components.CardPoster(
    modifier.animateItemPlacement(
        animationSpec = tween(durationMillis = 1000)
    ), data, loading, onClick
)

val cardPosterWidth = 150.dp
val cardPosterHeight = 187.5.dp


@Composable
fun CardPoster(
    modifier: Modifier = Modifier,
    data: Result,
    loading: Boolean = false,
    onClick: (Result) -> Unit
) {


    val spacer = 20.dp
    val heightTexts = 90.dp + spacer
    val circularProgressSize = 35.dp

    val height = cardPosterHeight + circularProgressSize + heightTexts

    Box(modifier = Modifier.width(cardPosterWidth).height(height)) {

        if (!loading) {

            Column(
                modifier = modifier.width(cardPosterWidth)
            ) {

                Box {
                    Card(
                        modifier = Modifier.fillMaxWidth().height(cardPosterHeight).clickable {
                            onClick(data)
                        }
                    ) {

                        val backDrop = Constant.IMAGE_BASE_PATH_CARD + data.posterPath
                        val painter = rememberImagePainter(backDrop, placeholderPainter = {
                            placeholderPainter(
                                width = cardPosterWidth.dpToPx(),
                                height = cardPosterHeight.dpToPx()
                            )
                        })
                        Image(
                            painter = painter,
                            contentDescription = "image",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }


                    Column(
                        modifier = Modifier.padding(
                            top = cardPosterHeight - circularProgressSize / 2,
                            start = 7.5.dp,
                            end = 7.5.dp
                        )
                            .height(heightTexts + circularProgressSize)

                    ) {
                        val progress = (data.voteAverage?.times(10f))?.toFloat() ?: 0f
                        val colorProgress = when (progress) {
                            in 1f..70f -> Color(0xFFD2D531)
                            in 71f..100f -> Color(0xFF21D07A)
                            else -> Color.Transparent
                        }

                        CircularProgressbar1(
                            size = circularProgressSize,
                            backgroundColor = MaterialTheme.colorScheme.primary,
                            backgroundInternalColor = MaterialTheme.colorScheme.primary,
                            foregroundIndicatorColor = colorProgress,
                            indicatorThickness = 2.5.dp,
                            backgroundInternalStroke = 1.5.dp,
                            textStyle = TextStyle(
                                fontSize = 10.sp,
                                color = Color.White,
                                fontWeight = FontWeight.SemiBold
                            ),
                            initialValue = progress
                        )

                        Spacer(modifier = Modifier.height(spacer / 2))
                        Text(
                            text = data.title ?: data.name ?: "",
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.labelLarge,
                            maxLines = 3,
                        )
                        Spacer(modifier = Modifier.height(7.5.dp))
                        Text(
                            text = data.releaseDate ?: data?.firstAirDate ?: "",
                            color = Color.Gray,
                            style = MaterialTheme.typography.labelLarge,
                            maxLines = 1,

                            )
                    }

                }


            }
        }
    }

}

@Composable
fun placeholderPainter(height: Float, width: Float): Painter {
    val customPainter = remember {
        object : Painter() {
            override val intrinsicSize: Size
                get() = Size(height = height, width = width)

            override fun DrawScope.onDraw() {
                drawRoundRect(
                    size = size,
                    color = Color.LightGray,
                    cornerRadius = CornerRadius.Zero
                )
            }
        }
    }
    return customPainter

}
