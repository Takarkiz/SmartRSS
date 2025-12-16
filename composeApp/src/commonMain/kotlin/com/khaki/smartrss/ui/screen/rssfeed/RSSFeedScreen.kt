package com.khaki.smartrss.ui.screen.rssfeed

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RSSFeedScreen(
    title: String,
    url: String,
    onRequestUrlChange: (String) -> Unit,
    onBack: () -> Unit,
) {
    Scaffold(
        modifier = Modifier,
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
                }
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
                onRequestUrlChange(url)
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


