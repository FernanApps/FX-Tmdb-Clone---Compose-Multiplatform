package pe.fernan.kmp.tmdb.ui.components

import androidx.compose.runtime.Composable
import kotlinx.browser.document
import org.w3c.dom.HTMLDivElement
import org.w3c.dom.HTMLIFrameElement
import org.w3c.dom.events.EventListener

@Composable
internal actual fun YoutubeDialogScreen(url: String, onClose: () -> Unit) {
    setupCloseIframe(onClose)
    showVideo(url)
}



/*
function showVideo(videoUrl) {
    var iframe = document.getElementById('videoIframe');
    iframe.src = videoUrl;
    document.getElementById('overlay').style.display = 'flex';
}
function closeVideo() {
    document.getElementById('overlay').style.display = 'none';
}


// Call Js Code in Html
@JsName("showVideo")
external fun showVideo()
@JsName("closeVideo")
external fun closeVideo()


 */

private val overlayElement by lazy {
    document.getElementById("overlay") as? HTMLDivElement
}
private val closeButtonElement by lazy {
    document.getElementById("closeButton")
}
private val iframeElement by lazy {
    document.getElementById("videoIframe") as? HTMLIFrameElement
}


private fun setupCloseIframe(onClose: () -> Unit) {
    overlayElement?.addEventListener("click", EventListener {
        closeVideo()
        onClose()
    })
    closeButtonElement?.addEventListener("click", EventListener {
        closeVideo()
        onClose()
    })
}

private fun showVideo(videoUrl: String) {
    iframeElement?.src = videoUrl
    overlayElement?.style?.display = "flex"
}

private fun closeVideo() {
    overlayElement?.style?.display = "none"
    iframeElement?.src = ""
}
