package com.khaki.smartrss.ui.screen.setting.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.khaki.smartrss.ui.screen.setting.model.RegisterableRssGroup
import com.khaki.smartrss.ui.screen.setting.model.RegisteredRssGroup
import com.khaki.smartrss.ui.screen.setting.model.RegisteredRssGroupPreviewParameterProvider
import com.khaki.smartrss.ui.theme.SmartRssTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
internal fun RegisteredRssGroup(
    targetGroup: RegisterableRssGroup,
    registeredRss: List<RegisteredRssGroup>,
    onClickAddButton: (RegisterableRssGroup) -> Unit,
    onClickGroupItem: (RegisteredRssGroup) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(
                shape = MaterialTheme.shapes.large
            )
            .background(
                color = MaterialTheme.colorScheme.surfaceVariant,
                shape = MaterialTheme.shapes.large
            )
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        Text(
            text = targetGroup.displayName,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        if (registeredRss.isNotEmpty()) {

            registeredRss.forEach { item ->
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(
                            MaterialTheme.shapes.small
                        )
                        .clickable(
                            onClick = {
                                onClickGroupItem(item)
                            },
                            interactionSource = remember { MutableInteractionSource() },
                            indication = ripple(color = MaterialTheme.colorScheme.primary)
                        ),
                ) {

                    Text(
                        text = item.name,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Text(
                        text = item.description,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Text(
                        modifier = Modifier
                            .clip(
                                shape = MaterialTheme.shapes.small
                            )
                            .background(
                                color = MaterialTheme.colorScheme.surfaceContainer
                            )
                            .padding(2.dp),
                        text = item.url,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

        }

        Button(
            onClick = {
                onClickAddButton(targetGroup)
            },
            modifier = Modifier
                .align(Alignment.End)
        ) {
            Text(text = "追加する")
        }
    }
}

@Preview
@Composable
private fun RegisteredRssGroupPreview() {

    SmartRssTheme {
        RegisteredRssGroup(
            targetGroup = RegisterableRssGroup.Qiita,
            registeredRss = RegisteredRssGroupPreviewParameterProvider().values.toList(),
            onClickAddButton = {},
            onClickGroupItem = {}
        )
    }
}