
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.CanvasBasedWindow
import org.jetbrains.skiko.wasm.onWasmReady
import pe.fernan.kmp.tmdb.App

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    onWasmReady {
        CanvasBasedWindow("FX-Tmdb - Compose Multiplatform") {
            /*
            var loading: Boolean by remember { mutableStateOf(true) }

            if (loading) {
                LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
            } else {

            }

            LaunchedEffect(Unit) {
                loadMontserratFont()
                if(loading){
                    loading = false
                }
            }

             */
            App()

        }
    }
}
