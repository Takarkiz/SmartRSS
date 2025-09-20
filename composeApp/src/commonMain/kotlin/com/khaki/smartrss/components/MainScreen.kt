package com.khaki.smartrss.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import com.khaki.smartrss.ui.screen.recomend.RecommendContent
import com.khaki.smartrss.ui.screen.recomend.model.FeedItemUiModelPreviewProvider
import com.khaki.smartrss.ui.screen.rss.RssContent
import com.khaki.smartrss.ui.screen.rss.RssUiStatePreviewParameterProvider
import com.khaki.smartrss.ui.theme.SmartRssTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {

    var currentTab by remember { mutableStateOf(AppTabs.Recommended) }
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = currentTab.title, // Update title based on current tab
                    )
                },
                scrollBehavior = scrollBehavior
            )
        },
        bottomBar = {
            NavigationBar {
                AppTabs.entries.forEach { tab -> // Removed Indexed as index is not used
                    NavigationBarItem(
                        selected = currentTab == tab,
                        onClick = {
                            currentTab = tab
                        },
                        label = {
                            Text(
                                text = tab.title
                            )
                        },
                        icon = {
                            Icon(
                                imageVector = tab.icon,
                                contentDescription = tab.title,
                            )
                        }
                    )
                }
            }
        },
    ) { innerPadding ->
        when (currentTab) {
            AppTabs.Recommended -> {
                RecommendContent(
                    feedItems = FeedItemUiModelPreviewProvider().values.toList(),
                    onClickItem = { /* Handle item click */ },
                    modifier = Modifier.padding(innerPadding)
                )
            }

            AppTabs.All -> {

            }

            AppTabs.Bookmarks -> {

            }

            AppTabs.RSS -> {
                RssContent(
                    onClickRssItem = {
                        // TODO: Implement action
                    },
                    // TODO: サンプルの値を入れる
                    uiState = RssUiStatePreviewParameterProvider().values.first(),
                    modifier = Modifier.padding(innerPadding)
                )
            }
        }
    }

}

@Preview
@Composable
private fun PreviewMainScreen() {
    SmartRssTheme {
        MainScreen()
    }
}
