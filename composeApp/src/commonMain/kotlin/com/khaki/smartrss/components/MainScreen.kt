package com.khaki.smartrss.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import com.khaki.smartrss.ui.screen.allfeeds.AllFeedsContent
import com.khaki.smartrss.ui.screen.allfeeds.AllFeedsViewModel
import com.khaki.smartrss.ui.screen.bookmark.BookmarkFeedsContent
import com.khaki.smartrss.ui.screen.bookmark.BookmarkFeedsViewModel
import com.khaki.smartrss.ui.screen.recomend.RecommendFeedsContent
import com.khaki.smartrss.ui.screen.rss.RssContent
import com.khaki.smartrss.ui.screen.rss.RssViewModel
import com.khaki.smartrss.ui.theme.SmartRssTheme
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.koinInject

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    onFeedClick: (String, String) -> Unit,
    onSettingClick: () -> Unit,
) {

    var currentTab by rememberSaveable { mutableStateOf(AppTabs.Recommended) }
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    val snackbarHostState = remember { SnackbarHostState() }

    val allFeedsViewModel = koinInject<AllFeedsViewModel>()
    val bookmarkFeedsViewModel = koinInject<BookmarkFeedsViewModel>()
    val rssViewModel = koinInject<RssViewModel>()

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
                actions = {
                    if (currentTab in listOf(AppTabs.Recommended, AppTabs.AllFeeds, AppTabs.Bookmarks)) {
                        IconButton(onClick = onSettingClick) {
                            Icon(
                                imageVector = Icons.Default.Settings,
                                contentDescription = "設定"
                            )
                        }
                    }
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
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { innerPadding ->
        when (currentTab) {
            AppTabs.Recommended -> {
                RecommendFeedsContent()
            }

            AppTabs.AllFeeds -> {
                val uiState by allFeedsViewModel.uiState.collectAsState()

                AllFeedsContent(
                    uiState = uiState,
                    onRefresh = {
                        allFeedsViewModel.refresh()
                    },
                    onClickItem = { id, url ->
                        allFeedsViewModel.doAsRead(id)
                        onFeedClick(id, url)
                    },
                    onClickBookmark = { id ->
                        allFeedsViewModel.updateBookmarkState(id)
                    },
                    onClickGood = { id ->
                        allFeedsViewModel.updateGoodState(id)
                    },
                    onClickBad = { id ->
                        allFeedsViewModel.updateBadState(id)
                    },
                    modifier = Modifier.padding(innerPadding)
                )
            }

            AppTabs.Bookmarks -> {
                val uiState by bookmarkFeedsViewModel.uiState.collectAsState()

                BookmarkFeedsContent(
                    uiState = uiState,
                    onClickItem = { id, url ->
                        bookmarkFeedsViewModel.doAsRead(id)
                        onFeedClick(id, url)
                    },
                    onClickBookmark = { id ->
                        bookmarkFeedsViewModel.updateBookmarkState(id)
                    },
                    onClickGood = { id ->
                        bookmarkFeedsViewModel.updateGoodState(id)
                    },
                    onClickBad = {
                        bookmarkFeedsViewModel.updateBadState(it)
                    },
                    modifier = Modifier.padding(innerPadding)
                )
            }

            AppTabs.RSS -> {
                val uiState by rssViewModel.uiState.collectAsState()

                LaunchedEffect(rssViewModel) {
                    rssViewModel.errorMessageFlow.collect { message ->
                        snackbarHostState.showSnackbar(message)
                    }
                }

                RssContent(
                    uiState = uiState,
                    onClickRssItem = {
                        // TODO: Implement item click action when navigating to a feed list/detail
                    },
                    onConfirmItem = { group, form ->
                        rssViewModel.appendRssFeed(group, form)
                    },
                    onExpandBottomSheet = {
                        rssViewModel.updateExpandedBottomSheet(it)
                    },
                    onDismissRequest = {
                        rssViewModel.updateExpandedBottomSheet(null)
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
        MainScreen(
            onFeedClick = { _, _ -> },
            onSettingClick = {}
        )
    }
}
