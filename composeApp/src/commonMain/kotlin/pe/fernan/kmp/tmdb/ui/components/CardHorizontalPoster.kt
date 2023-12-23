package pe.fernan.kmp.tmdb.ui.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.PointerType
import androidx.compose.ui.input.pointer.onPointerEvent
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.seiko.imageloader.rememberImagePainter
import pe.fernan.kmp.tmdb.domain.model.Result
import pe.fernan.kmp.tmdb.utils.Constant

@OptIn(ExperimentalFoundationApi::class, ExperimentalComposeUiApi::class)
@Composable
fun CardHorizontalPoster(
    data: Result,
    loading: Boolean = false,
    onMouseover: (String?) -> Unit,
    onClick: (Result) -> Unit
) {

    val cardHeight = 180.dp
    val cardWidth = 250.dp

    val space = 10.dp
    val textHeight = 60.dp

    val height = cardHeight + space + textHeight

    Box(modifier = Modifier.width(cardWidth).height(height)) {
        if (!loading) {
            Column(modifier = Modifier.height(height).width(cardWidth)) {

                Card(
                    modifier = Modifier.height(cardHeight).fillMaxWidth()/*.aspectRatio(3 / 2f, true)
                .padding(end = paddingInternal / 2).onGloballyPositioned { coordinates ->
                    cardWidth = coordinates.size.width
                }*/.clickable {
                        onClick(data)
                    }.onPointerEvent(PointerEventType.Move) { focusState ->

                        /*

                        INFO: [INFO] CardHorizontalPosterKt$CardHorizontalPoster$invoke - Is fa ---------
                        Em Fogo Alto

                        PointerEvent(
                        changes=[
                            PointerInputChange(
                                id=PointerId(value=0),
                                uptimeMillis=1703199437020,
                                position=Offset(276.5, 196.3),
                                pressed=false,
                                pressure=1.0,
                                previousUptimeMillis=1703199437020,
                                previousPosition=Offset(276.5, 196.3),
                                previousPressed=false, i
                                sConsumed=false,
                                type=Mouse, historical=[],
                                scrollDelta=Offset(0.0, 0.0))
                        ],
                        buttons=PointerButtons(packedValue=0), keyboardModifiers=PointerKeyboardModifiers(packedValue=512),
                        nativeEvent=java.awt.event.MouseEvent[MOUSE_MOVED,(246,297),absolute(466,368),clickCount=0] on canvas0, button=null, type=Move)

                         */
                        if (focusState.changes.firstOrNull()?.type == PointerType.Mouse) {
                            onMouseover(data.posterPath)
                        }

                    }
                ) {
                    val backDrop = Constant.IMAGE_BASE_PATH_CARD_HORIZONTAL + data.backdropPath
                    val painter = rememberImagePainter(backDrop)
                    Image(
                        painter = painter,
                        contentDescription = "image",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                }
                Spacer(modifier = Modifier.height(space))
                Column(
                    Modifier.width(cardWidth).height(textHeight),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {


                    Text(
                        text = data.title ?: data.originalTitle ?: data.name ?: data.originalName ?: "",
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(horizontal = 15.dp),
                        textAlign = TextAlign.Center,
                        maxLines = 1
                    )
                    Text(
                        text = data.originalTitle ?: "",
                        color = Color.White,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(horizontal = 10.dp),
                        textAlign = TextAlign.Center,
                        maxLines = 1

                    )
                }

            }
        }
    }

}