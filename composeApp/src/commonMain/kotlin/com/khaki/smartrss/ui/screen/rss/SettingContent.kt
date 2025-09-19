package com.khaki.smartrss.ui.screen.rss

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridItemSpan
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.khaki.smartrss.ui.screen.rss.composable.RegisteredRssGroupCard
import com.khaki.smartrss.ui.screen.rss.model.RegisterableRssGroup
import com.khaki.smartrss.ui.screen.rss.model.RegisteredRssGroup
import com.khaki.smartrss.ui.screen.rss.model.RegisteredRssGroupPreviewParameterProvider
import com.khaki.smartrss.ui.theme.SmartRssTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
internal fun RssContent(
    onClickAddItem: (RegisterableRssGroup) -> Unit,
    onClickRssItem: (RegisteredRssGroup) -> Unit,
    modifier: Modifier = Modifier
) {
    BoxWithConstraints(modifier = modifier.fillMaxSize()) {
        val columns = if (maxWidth < 600.dp) {
            StaggeredGridCells.Fixed(1)
        } else {
            StaggeredGridCells.Fixed(2)
        }

        // サンプル用のデータを用意
        val sampleRegisteredRssList =
            remember { RegisteredRssGroupPreviewParameterProvider().values.toList() }

        LazyVerticalStaggeredGrid(
            columns = columns,
            contentPadding = PaddingValues(12.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalItemSpacing = 16.dp,
            modifier = Modifier // Modifier is applied to BoxWithConstraints, this fills the box
                .fillMaxSize()
                .background(
                    color = MaterialTheme.colorScheme.background
                )
        ) {

            item(
                key = "header",
                span = StaggeredGridItemSpan.FullLine
            ) {
                Text(
                    text = "登録済みRSS一覧",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier
                        .padding(8.dp)
                )
            }

            items(
                count = RegisterableRssGroup.entries.size,
                key = { index -> RegisterableRssGroup.entries[index].name }
            ) { index ->
                RegisteredRssGroupCard(
                    // Use a defined value from the placeholder enum RegisterableRssGroup
                    targetGroup = RegisterableRssGroup.entries[index],
                    registeredRss = sampleRegisteredRssList,
                    onClickAddButton = {
                        onClickAddItem(it)
                    },
                    onClickGroupItem = {
                        onClickRssItem(it)
                    }
                )
            }
        }
    }
}

@Preview
@Composable
fun RssContentPreview_Normal() {
    SmartRssTheme {
        Surface {
            RssContent(
                onClickAddItem = {},
                onClickRssItem = {},
            )
        }
    }
}
