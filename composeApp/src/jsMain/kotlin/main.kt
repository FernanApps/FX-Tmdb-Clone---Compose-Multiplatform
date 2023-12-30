
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
import org.jetbrains.skiko.wasm.onWasmReady
import pe.fernan.kmp.tmdb.App
import pe.fernan.kmp.tmdb.loadFontResource
import pe.fernan.kmp.tmdb.theme.AppFont


// https://github.com/JetBrains/compose-multiplatform/issues/3172
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
                //loadKarlaFont()
                //prepareImagesCache()
                if(loading){
                    loading = false
                }
            }




        }
    }
}

private suspend fun loadMontserratFont() {
    val sourceSansProBold = loadFontResource("SourceSansPro-Bold.otf")
    val sourceSansProExtraLightIt = loadFontResource("SourceSansPro-ExtraLightIt.otf")
    val sourceSansProLightIt = loadFontResource("SourceSansPro-LightIt.otf")
    val sourceSansProSemiboldIt = loadFontResource("SourceSansPro-SemiboldIt.otf")
    val sourceSansProBlack = loadFontResource("SourceSansPro-Black.otf")
    val sourceSansProBoldIt = loadFontResource("SourceSansPro-BoldIt.otf")
    val sourceSansProIt = loadFontResource("SourceSansPro-It.otf")
    val sourceSansProRegular = loadFontResource("SourceSansPro-Regular.otf")
    val sourceSansProBlackIt = loadFontResource("SourceSansPro-BlackIt.otf")
    val sourceSansProExtraLight = loadFontResource("SourceSansPro-ExtraLight.otf")
    val sourceSansProLight = loadFontResource("SourceSansPro-Light.otf")
    val sourceSansProSemibold = loadFontResource("SourceSansPro-Semibold.otf")


    AppFont.CustomBaseFont = FontFamily(
        Font(identity = "SourceSansProBold", data = sourceSansProBold, weight = FontWeight.Bold),
        //Font(identity = "SourceSansProExtraLightIt", data = sourceSansProExtraLightIt, weight = FontWeight.ExtraLight),
        //Font(identity = "SourceSansProLightIt", data = sourceSansProLightIt, weight = FontWeight.Light),
        //Font(identity = "SourceSansProSemiboldIt", data = sourceSansProSemiboldIt, weight = FontWeight.SemiBold),
        Font(identity = "SourceSansProBlack", data = sourceSansProBlack, weight = FontWeight.Black),
        //Font(identity = "SourceSansProBoldIt", data = sourceSansProBoldIt, weight = FontWeight.Bold),
        //Font(identity = "SourceSansProIt", data = sourceSansProIt, weight = FontWeight.Normal),
        Font(identity = "SourceSansProRegular", data = sourceSansProRegular, weight = FontWeight.Normal),
        //Font(identity = "SourceSansProBlackIt", data = sourceSansProBlackIt, weight = FontWeight.Black),
        Font(identity = "SourceSansProExtraLight", data = sourceSansProExtraLight, weight = FontWeight.ExtraLight),
        Font(identity = "SourceSansProLight", data = sourceSansProLight, weight = FontWeight.Light),
        Font(identity = "SourceSansProSemibold", data = sourceSansProSemibold, weight = FontWeight.SemiBold)
    )

}




