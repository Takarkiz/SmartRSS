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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.khaki.modules.core.model.feed.FormType
import com.khaki.smartrss.ui.screen.rss.composable.RSSAdditionalFormContent
import com.khaki.smartrss.ui.screen.rss.composable.RegisteredRssGroupCard
import com.khaki.smartrss.ui.screen.rss.model.RegisterableRssGroup
import com.khaki.smartrss.ui.screen.rss.model.RegisteredRssGroup
import com.khaki.smartrss.ui.theme.SmartRssTheme
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.jetbrains.compose.ui.tooling.preview.PreviewParameter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun RssContent(
    uiState: RssUiState,
    onClickRssItem: (RegisteredRssGroup) -> Unit,
    onConfirmItem: (RegisterableRssGroup, FormType) -> Unit,
    modifier: Modifier = Modifier
) {
    BoxWithConstraints(modifier = modifier.fillMaxSize()) {
        val columns = if (maxWidth < 600.dp) {
            StaggeredGridCells.Fixed(1)
        } else {
            StaggeredGridCells.Fixed(2)
        }

        var selectedGroup: RegisterableRssGroup? by remember { mutableStateOf(null) }

        LazyVerticalStaggeredGrid(
            columns = columns,
            contentPadding = PaddingValues(
                top = 12.dp,
                start = 12.dp,
                end = 12.dp,
                bottom = 48.dp
            ),
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
                val group = RegisterableRssGroup.entries[index]
                RegisteredRssGroupCard(
                    targetGroup = group,
                    registeredRss = uiState.registeredRssGroupList[group] ?: emptyList(),
                    onClickAddButton = {
                        selectedGroup = it
                    },
                    onClickGroupItem = {
                        onClickRssItem(it)
                    }
                )
            }
        }

        selectedGroup?.let { group ->
            ModalBottomSheet(
                onDismissRequest = {
                    selectedGroup = null
                },
            ) {
                RSSAdditionalFormContent(
                    target = group,
                    inputForms = uiState.registerableRssFormat[group] ?: emptyList(),
                    onClickAddConfirm = { group, formType ->
                        onConfirmItem(group, formType)
                    }
                )
            }
        }
    }
}

@Preview
@Composable
fun RssContentPreview_Normal(
    @PreviewParameter(RssUiStatePreviewParameterProvider::class) uiState: RssUiState
) {
    SmartRssTheme {
        Surface {
            RssContent(
                uiState = uiState,
                onClickRssItem = {},
                onConfirmItem = { _, _ -> },
            )
        }
    }
}
