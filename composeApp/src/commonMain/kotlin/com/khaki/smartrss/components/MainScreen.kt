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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import com.khaki.smartrss.ui.screen.allfeeds.AllFeedsContent
import com.khaki.smartrss.ui.screen.bookmark.BookmarkFeedsContent
import com.khaki.smartrss.ui.screen.recomend.RecommendContent
import com.khaki.smartrss.ui.screen.recomend.RecommendViewModel
import com.khaki.smartrss.ui.screen.rss.RssContent
import com.khaki.smartrss.ui.screen.rss.RssViewModel
import com.khaki.smartrss.ui.theme.SmartRssTheme
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.koinInject

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
                        text = currentTab.title,
                    )
                },
                scrollBehavior = scrollBehavior
            )
        },
        bottomBar = {
            NavigationBar {
                AppTabs.entries.forEach { tab ->
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

                val recommendViewModel = koinInject<RecommendViewModel>()
                val recommendUiState by recommendViewModel.uiState.collectAsState()

                RecommendContent(
                    uiState = recommendUiState,
                    onClickItem = { /* Handle item click */ },
                    modifier = Modifier.padding(innerPadding)
                )
            }

            AppTabs.AllFeeds -> {
                AllFeedsContent()
            }

            AppTabs.Bookmarks -> {
                BookmarkFeedsContent()
            }

            AppTabs.RSS -> {
                val rssViewModel = koinInject<RssViewModel>()
                val uiState by rssViewModel.uiState.collectAsState()

                RssContent(
                    uiState = uiState,
                    onClickRssItem = {
                        // TODO: Implement item click action when navigating to a feed list/detail
                    },
                    onConfirmItem = { group, form ->
                        rssViewModel.appendRssFeed(group, form)
                    },
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
