
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.platform.Font
import androidx.compose.ui.window.CanvasBasedWindow
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.resource
import org.jetbrains.skiko.wasm.onWasmReady
import pe.fernan.kmp.tmdb.App
import pe.fernan.kmp.tmdb.theme.AppFont

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    onWasmReady {
        CanvasBasedWindow("FX-Tmdb - Compose Multiplatform") {
            var loading: Boolean by remember { mutableStateOf(true) }

            if (loading) {
                LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
            } else {
                App()
            }

            LaunchedEffect(Unit) {
                loadMontserratFont()
                if(loading){
                    loading = false
                }
            }

        }
    }
}

private suspend fun loadMontserratFont() {
    val regular = loadResource("montserrat_regular.ttf")
    val medium = loadResource("montserrat_medium.ttf")
    val light = loadResource("montserrat_light.ttf")
    val semiBold = loadResource("montserrat_semibold.ttf")

    AppFont.CustomBaseFont = FontFamily(
        Font(identity = "MontserratRegular", data = regular, weight = FontWeight.Normal),
        Font(identity = "MontserratMedium", data = medium, weight = FontWeight.Medium),
        Font(identity = "MontserratLight", data = light, weight = FontWeight.Light),
        Font(identity = "MontserratSemiBold", data = semiBold, weight = FontWeight.SemiBold),
    )
}

@OptIn(ExperimentalResourceApi::class)
internal suspend fun loadResource(resourcePath: String): ByteArray {
    return resource(resourcePath).readBytes()
}