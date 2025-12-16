package com.khaki.smartrss.platform

import androidx.compose.runtime.Composable

@Composable
actual fun ExternalBrowserLauncher(url: String?, onLaunched: () -> Unit) {
    androidx.compose.runtime.LaunchedEffect(url) {
        if (url != null) {
            // TODO: JVMでのブラウザ起動を実装する
            onLaunched()
        }
    }
}
