package com.khaki.smartrss.ui.screen.rssfeed

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RSSFeedScreen(
    title: String,
    url: String,
    onRequestUrlChange: (String) -> Unit,
    onBack: () -> Unit,
) {

    var requestedExternalBrowserUrl: String? by remember { mutableStateOf(null) }
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = title
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = onBack
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = null
                        )
                    }
                },
                scrollBehavior = scrollBehavior
            )
        },
    ) {
        RSSFeedContent(
            uiState = RSSFeedUiState(
                requestUrl = url
            ),
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            onRequestUrlChange = { url ->
                requestedExternalBrowserUrl = url
            }
        )
    }

    if (requestedExternalBrowserUrl != null) {
        AlertDialog(
            onDismissRequest = {
                requestedExternalBrowserUrl = null
            },
            title = {
                Text(
                    text = "別のページを開こうとしています"
                )
            },
            text = {
                Text(
                    text = "別のブラウザでこのページを開きますか？\n（$requestedExternalBrowserUrl）"
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        val url = requestedExternalBrowserUrl ?: return@TextButton
                        requestedExternalBrowserUrl = null
                        onRequestUrlChange(url)
                    }
                ) {
                    Text("開く")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        requestedExternalBrowserUrl = null
                    }
                ) {
                    Text("キャンセル")
                }
            }
        )
    }
}

@Composable
expect fun RSSFeedContent(
    modifier: Modifier = Modifier,
    uiState: RSSFeedUiState,
    onRequestUrlChange: (String) -> Unit
)
