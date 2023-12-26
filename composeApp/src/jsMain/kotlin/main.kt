
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.CanvasBasedWindow
import org.jetbrains.skiko.wasm.onWasmReady
import pe.fernan.kmp.tmdb.App


// https://github.com/JetBrains/compose-multiplatform/issues/3172
@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    onWasmReady {
        CanvasBasedWindow("FX-Tmdb - Compose Multiplatform") {
            App()
        }
    }
}




