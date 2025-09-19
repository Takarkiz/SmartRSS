package com.khaki.smartrss.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Feed
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.RssFeed
import androidx.compose.material.icons.filled.SmartToy
import androidx.compose.ui.graphics.vector.ImageVector

enum class AppTabs(val title: String, val icon: ImageVector) {
    Recommended("おすすめ", Icons.Default.SmartToy),
    All("すべて", Icons.AutoMirrored.Filled.Feed),
    Bookmarks("後で見る", Icons.Default.Bookmark),
    RSS("RSS", Icons.Default.RssFeed)
}
