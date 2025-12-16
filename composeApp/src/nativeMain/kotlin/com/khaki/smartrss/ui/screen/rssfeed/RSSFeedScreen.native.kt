package com.khaki.smartrss.ui.screen.rssfeed

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.interop.UIKitView
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.ObjCSignatureOverride
import platform.Foundation.NSURL
import platform.Foundation.NSURLRequest
import platform.WebKit.WKNavigation
import platform.WebKit.WKNavigationAction
import platform.WebKit.WKNavigationDelegateProtocol
import platform.WebKit.WKNavigationTypeFormSubmitted
import platform.WebKit.WKNavigationTypeLinkActivated
import platform.WebKit.WKWebView
import platform.darwin.NSObject

@OptIn(ExperimentalForeignApi::class)
@Composable
actual fun RSSFeedContent(
    modifier: Modifier,
    uiState: RSSFeedUiState,
    onRequestUrlChange: (String) -> Unit,
) {
    var isLoading by remember { mutableStateOf(false) }
    val currentOnRequestUrlChange by rememberUpdatedState(onRequestUrlChange)
    val currentUiState by rememberUpdatedState(uiState)

    val navigationDelegate = remember {
        object : NSObject(), WKNavigationDelegateProtocol {
            override fun webView(
                webView: WKWebView,
                decidePolicyForNavigationAction: WKNavigationAction,
                decisionHandler: (platform.WebKit.WKNavigationActionPolicy) -> Unit
            ) {
                when (decidePolicyForNavigationAction.navigationType) {
                    WKNavigationTypeLinkActivated, WKNavigationTypeFormSubmitted -> {
                        val newUrl = decidePolicyForNavigationAction.request.URL?.absoluteString
                        if (newUrl != null && newUrl != currentUiState.requestUrl) {
                            currentOnRequestUrlChange(newUrl)
                            decisionHandler(platform.WebKit.WKNavigationActionPolicy.WKNavigationActionPolicyCancel)
                            return
                        }
                    }

                    else -> {
                        // Allow other navigation types
                    }
                }
                decisionHandler(platform.WebKit.WKNavigationActionPolicy.WKNavigationActionPolicyAllow)
            }

            @ObjCSignatureOverride
            override fun webView(webView: WKWebView, didStartProvisionalNavigation: WKNavigation?) {
                isLoading = true
            }

            @ObjCSignatureOverride
            override fun webView(webView: WKWebView, didFinishNavigation: WKNavigation?) {
                isLoading = false
            }
        }
    }

    Box(modifier = modifier.fillMaxSize()) {
        UIKitView(
            factory = {
                WKWebView().apply {
                    this.navigationDelegate = navigationDelegate
                }
            },
            update = { webView ->
                if (webView.URL?.absoluteString != uiState.requestUrl) {
                    uiState.requestUrl.let {
                        val url = NSURL(string = it)
                        val request = NSURLRequest(uRL = url)
                        webView.loadRequest(request)
                    }
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
