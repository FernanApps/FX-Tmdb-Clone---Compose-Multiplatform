package pe.fernan.kmp.tmdb.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.multiplatform.webview.web.LoadingState
import com.multiplatform.webview.web.rememberWebViewNavigator
import com.multiplatform.webview.web.rememberWebViewState

@Composable
internal actual fun YoutubeDialogScreen(url: String, onClose: () -> Unit) = WebView(url, onClose)

@Composable
private fun WebView(url: String, onCloseDialog: () -> Unit) {
    var showDialog by remember { mutableStateOf(true) }

    if (showDialog) {
        Dialog(
            onDismissRequest = {
                showDialog = false
                onCloseDialog()
            }, properties = DialogProperties(
                usePlatformDefaultWidth = false
            )
        ) {
            // Custom shape, background, and layout for the dialog
            Surface(
                modifier = Modifier.fillMaxSize(0.75f),
                color = Color.Transparent
            ) {
                val webViewState = rememberWebViewState(url)
                val navigator = rememberWebViewNavigator()

                /*
                webViewState.webSettings.apply {
                    isJavaScriptEnabled = true
                    androidWebSettings.apply {
                        //isAlgorithmicDarkeningAllowed = true
                        //safeBrowsingEnabled = true
                    }
                }
                 */

                Column {
                    Row {
                        Spacer(modifier = Modifier.weight(1f))
                        FilledIconButton(onClick = {
                            showDialog = false
                            onCloseDialog()
                        }, modifier = Modifier.scale(0.9f)) {
                            Icon(Icons.Outlined.Close, contentDescription = "Close")
                        }

                    }

                    val loadingState = webViewState.loadingState
                    if (loadingState is LoadingState.Loading) {
                        LinearProgressIndicator(
                            progress = loadingState.progress,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                    com.multiplatform.webview.web.WebView(
                        state = webViewState,
                        //navigator = navigator,
                        modifier = Modifier.fillMaxSize()
                    )

                }


            }
        }

    }
}