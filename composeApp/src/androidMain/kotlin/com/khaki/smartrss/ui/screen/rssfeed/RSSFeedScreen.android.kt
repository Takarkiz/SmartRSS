package com.khaki.smartrss.ui.screen.rssfeed

import android.graphics.Bitmap
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView

@Composable
actual fun RSSFeedContent(
    modifier: Modifier,
    uiState: RSSFeedUiState,
    onRequestUrlChange: (String) -> Unit,
) {
    var isLoading by remember { mutableStateOf(false) }

    Box(modifier = modifier.fillMaxSize()) {
        AndroidView(
            factory = { context ->
                WebView(context).apply {
                    webViewClient = object : WebViewClient() {
                        override fun shouldOverrideUrlLoading(
                            view: WebView?,
                            request: WebResourceRequest?
                        ): Boolean {
                            val newUrl = request?.url?.toString()
                            if (newUrl != null && newUrl != uiState.requestUrl) {
                                onRequestUrlChange(newUrl)
                                return true // Prevent navigation
                            }
                            return false // Allow navigation
                        }

                        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                            super.onPageStarted(view, url, favicon)
                            isLoading = true
                        }

                        override fun onPageFinished(view: WebView?, url: String?) {
                            super.onPageFinished(view, url)
                            isLoading = false
                        }
                    }
                    loadUrl(uiState.requestUrl)
                }
            },
            update = { webView ->
                if (webView.url != uiState.requestUrl) {
                    webView.loadUrl(uiState.requestUrl)
                }
            },
            modifier = Modifier.fillMaxSize()
        )

        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}

@Preview
@Composable
private fun PreviewRSSFeedContent() {
    RSSFeedContent(
        modifier = Modifier.fillMaxSize(),
        uiState = RSSFeedUiState(
            requestUrl = "https://www.google.com"
        ),
        onRequestUrlChange = {}
    )
}
