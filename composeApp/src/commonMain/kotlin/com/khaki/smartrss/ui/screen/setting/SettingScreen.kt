package com.khaki.smartrss.ui.screen.setting

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.khaki.smartrss.ui.screen.setting.composable.SettingButtonItem
import com.khaki.smartrss.ui.screen.setting.composable.SettingSwitchItem
import org.jetbrains.compose.ui.tooling.preview.Preview

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.TextButton
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.window.DialogProperties
import com.khaki.smartrss.ui.screen.setting.composable.ButtonType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingScreen(
    uiState: SettingUiState,
    onClickSummaryEnable: (Boolean) -> Unit,
    onBack: () -> Unit,
    onDeleteAllFeeds: () -> Unit,
) {
    var showDeleteAllFeedsDialog by remember { mutableStateOf(false) }

    if (showDeleteAllFeedsDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteAllFeedsDialog = false },
            title = {
                Text(text = "フィードを全て削除しますか？")
            },
            text = {
                Text(text = "この操作は取り消せません。")
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        onDeleteAllFeeds()
                        showDeleteAllFeedsDialog = false
                    }
                ) {
                    Text("削除")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { showDeleteAllFeedsDialog = false }
                ) {
                    Text("キャンセル")
                }
            },
            properties = DialogProperties(
                dismissOnBackPress = true,
                dismissOnClickOutside = true
            )
        )
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(text = "アプリ設定")
                },
                navigationIcon = {
                    IconButton(
                        onClick = onBack
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "戻る"
                        )
                    }
                }
            )
        }
    ) {
        SettingContent(
            uiState = uiState,
            onClickSummaryEnable = onClickSummaryEnable,
            onShowDeleteAllFeedsDialog = { showDeleteAllFeedsDialog = true },
            modifier = Modifier
                .padding(it)
        )
    }
}

@Composable
private fun SettingContent(
    uiState: SettingUiState,
    onClickSummaryEnable: (Boolean) -> Unit,
    onShowDeleteAllFeedsDialog: () -> Unit,
    modifier: Modifier = Modifier,
) {

    LazyColumn(
        modifier = modifier
            .fillMaxSize(),
        contentPadding = PaddingValues(
            vertical = 12.dp
        )
    ) {

        SettingItem.entries.groupBy { it.category }.forEach { (category, items) ->

            stickyHeader {
                Text(
                    text = category.title,
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.tertiary,
                    modifier = Modifier
                        .padding(
                            horizontal = 16.dp
                        )
                )
            }

            items(
                items = items,
            ) { item ->
                when (item.type) {
                    SettingType.SWITCH -> {
                        SettingSwitchItem(
                            title = item.title,
                            subtitle = item.subTitle,
                            isChecked = uiState.isSummaryEnabled,
                            onChangeSwitchValue = {
                                onClickSummaryEnable(it)
                            }
                        )
                    }

                    SettingType.BUTTON -> {
                        SettingButtonItem(
                            title = item.title,
                            onClick = onShowDeleteAllFeedsDialog,
                            buttonType = ButtonType.DESTRUCTIVE
                        )
                    }
                }
            }
        }


    }
}

@Preview(
    showBackground = true
)
@Composable
private fun PreviewSettingScreen() {
    MaterialTheme {
        SettingContent(
            uiState = SettingUiState(),
            onClickSummaryEnable = {},
            onShowDeleteAllFeedsDialog = {}
        )
    }
}

