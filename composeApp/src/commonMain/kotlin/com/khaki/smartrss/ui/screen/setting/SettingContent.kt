package com.khaki.smartrss.ui.screen.setting

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
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.khaki.smartrss.ui.screen.setting.composable.RegisteredRssGroup
import com.khaki.smartrss.ui.screen.setting.model.RegisterableRssGroup // Make sure this enum has BLOG or adapt
import com.khaki.smartrss.ui.screen.setting.model.RegisteredRssGroup
import com.khaki.smartrss.ui.screen.setting.model.RegisteredRssGroupPreviewParameterProvider
import com.khaki.smartrss.ui.theme.SmartRssTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
internal fun SettingContent(
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

            // サンプル用のデータを使用
            val sampleRegisteredRssList =
                RegisteredRssGroupPreviewParameterProvider().values.toList()
            items(
                count = RegisterableRssGroup.entries.size,
                key = { index -> RegisterableRssGroup.entries[index].name }
            ) { index ->
                RegisteredRssGroup(
                    // Use a defined value from the placeholder enum RegisterableRssGroup
                    targetGroup = RegisterableRssGroup.entries[index],
                    registeredRss = sampleRegisteredRssList,
                    onCLickAddButton = {
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
fun SettingContentPreview_Normal() {
    SmartRssTheme {
        Surface {
            SettingContent(
                onClickAddItem = {},
                onClickRssItem = {},
            )
        }
    }
}
