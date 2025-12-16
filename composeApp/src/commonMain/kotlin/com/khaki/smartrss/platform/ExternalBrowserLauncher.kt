package com.khaki.smartrss.platform

import androidx.compose.runtime.Composable

@Composable
expect fun ExternalBrowserLauncher(
    url: String?,
    onLaunched: () -> Unit
)
