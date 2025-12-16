package com.khaki.smartrss.platform

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.core.net.toUri

@Composable
actual fun ExternalBrowserLauncher(
    url: String?,
    onLaunched: () -> Unit
) {
    val context: Context = LocalContext.current
    LaunchedEffect(url) {
        if (url == null) return@LaunchedEffect

        val intent = Intent(Intent.ACTION_VIEW, url.toUri()).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        context.startActivity(intent)
        onLaunched()
    }
}
