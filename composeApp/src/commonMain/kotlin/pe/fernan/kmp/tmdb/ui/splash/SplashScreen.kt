package pe.fernan.kmp.tmdb.ui.splash

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.BaselineShift
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import pe.fernan.kmp.tmdb.subTitleTextStyle

@OptIn(ExperimentalResourceApi::class)
@Composable
fun SplashScreen(onNavigate: () -> Unit) {
    val scale = remember {
        androidx.compose.animation.core.Animatable(.85f)
    }
    LaunchedEffect(key1 = true) {
        scale.animateTo(
            targetValue = 1f,
            animationSpec = tween(
                durationMillis = 1500,
                easing = LinearEasing
            )
        )
        delay(3000L)
        onNavigate()
    }

    val brush = Brush.verticalGradient(
        colors = listOf(
            MaterialTheme.colorScheme.primary, Color(0xFF0B1E2A)
        )
    )
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize().background(brush)
    ) {
        Spacer(Modifier.size(100.dp))
        Image(
            painter = painterResource("logo_short.xml"),
            contentDescription = null,
            modifier = Modifier.scale(scale.value).height(30.dp),

            )
        Spacer(Modifier.size(20.dp))

        //Text("Hol")
        val normal = SpanStyle(
            fontSize = subTitleTextStyle.fontSize,
        )
        val superscript = SpanStyle(
            baselineShift = BaselineShift.Superscript,
            fontSize = subTitleTextStyle.fontSize.times(0.60f),
        )
        val subscript = SpanStyle(
            baselineShift = BaselineShift.Subscript,
            fontSize = subTitleTextStyle.fontSize.times(0.9f)
        )

        Text(
            text = buildAnnotatedString {
                withStyle(normal) {
                    append("Tmdb Clone")
                }
                append("\n")
                withStyle(superscript) {
                    append("by FernanApps")
                }
            },
            textAlign = TextAlign.End,
            style = subTitleTextStyle,
            color = Color(0xFFDBE8D7),
            )
    }
}