package com.khaki.smartrss.platform

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import platform.Foundation.NSURL
import platform.UIKit.UIApplication

@Composable
actual fun ExternalBrowserLauncher(
    url: String?,
    onLaunched: () -> Unit
) {
    LaunchedEffect(url) {
        if (url == null) return@LaunchedEffect

        val nsUrl = NSURL(string = url)
        if (UIApplication.sharedApplication.canOpenURL(nsUrl)) {
            UIApplication.sharedApplication.openURL(nsUrl)
        }
        onLaunched()
    }
}
