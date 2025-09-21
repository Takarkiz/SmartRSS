package com.khaki.smartrss.ui.screen.rss.composable

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.khaki.smartrss.ui.screen.rss.model.RssInputFormType
import com.khaki.smartrss.ui.theme.SmartRssTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun RssInputForm(
    model: RssInputFormType,
    isExpanded: Boolean,
    inputValue: String,
    onClick: () -> Unit,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
) {

    Column(
        modifier = modifier
            .fillMaxWidth(),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(
                    onClick = {
                        onClick()
                    },
                    interactionSource = remember { MutableInteractionSource() },
                    indication = ripple()
                )
                .padding(
                    horizontal = 4.dp,
                    vertical = 8.dp
                ),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Icon(
                imageVector = model.iconImageVector,
                contentDescription = model.title,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .size(24.dp),
            )

            Text(
                text = model.title, // Use model.title for dynamic text
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground
            )
        }

        AnimatedVisibility(
            visible = isExpanded,
            enter = fadeIn() + expandVertically(),
            exit = fadeOut() + shrinkVertically()
        ) {

            if (model == RssInputFormType.POPULAR) {

                Column(
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                ) {

                    var checkState by remember { mutableStateOf(false) }

                    Text(
                        text = "人気記事は特定のユーザーやタグに依存しない、全体の人気記事を表示します。購読する場合はチェックを入れて追加をしてください。",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f),
                        modifier = Modifier
                            .padding(horizontal = 4.dp)
                    )

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable(
                                onClick = {
                                    checkState = !checkState
                                    onValueChange(if (checkState) "true" else "")
                                },
                                interactionSource = remember { MutableInteractionSource() },
                                indication = ripple()
                            )
                    ) {

                        Checkbox(
                            checked = checkState,
                            onCheckedChange = {
                                checkState = !checkState
                                onValueChange(if (checkState) "true" else "")
                            },
                        )

                        Text(
                            text = "人気記事を購読する",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    }

                }
            } else {

                OutlinedTextField(
                    value = inputValue,
                    onValueChange = {
                        onValueChange(it)
                    },
                    label = {
                        Text(
                            text = when (model) {
                                RssInputFormType.POPULAR -> "人気記事"
                                RssInputFormType.TAG -> "タグ名"
                                RssInputFormType.USER -> "ユーザー名"
                                RssInputFormType.URL -> "RSSフィードのURL"
                            }
                        )
                    },
                    placeholder = {
                        Text(
                            text = when (model) {
                                RssInputFormType.POPULAR -> "人気記事"
                                RssInputFormType.TAG -> "例: kotlin"
                                RssInputFormType.USER -> "例: JetBrains"
                                RssInputFormType.URL -> "例: https://example.com/rss"
                            }
                        )
                    },
                    singleLine = true,
                    textStyle = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 4.dp)
                )
            }
        }
    }
}

@Preview()
@Composable
private fun RssInputFormPreview_Collapsed() {
    SmartRssTheme {
        RssInputForm(
            model = RssInputFormType.USER,
            isExpanded = false,
            inputValue = "JetBrains",
            onClick = {},
            onValueChange = {},
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
        )
    }
}

@Preview()
@Composable
private fun RssInputFormPreview_Expanded() {
    SmartRssTheme {
        RssInputForm(
            model = RssInputFormType.TAG,
            isExpanded = true,
            inputValue = "https://example.com/rss",
            onClick = {},
            onValueChange = {},
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
        )
    }
}
