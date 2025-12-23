package com.khaki.smartrss.ui.screen.rss.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.khaki.modules.core.model.feed.FormType
import com.khaki.modules.core.model.feed.Popular
import com.khaki.modules.core.model.feed.Tag
import com.khaki.modules.core.model.feed.URL
import com.khaki.modules.core.model.feed.UserId
import com.khaki.smartrss.ui.screen.rss.model.RegisterableRssGroup
import com.khaki.smartrss.ui.screen.rss.model.RssInputFormType
import com.khaki.smartrss.ui.screen.rss.model.RssInputFormTypePreviewParameterProvider
import com.khaki.smartrss.ui.theme.SmartRssTheme
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
internal fun RSSAdditionalFormContent(
    target: RegisterableRssGroup,
    inputForms: List<RssInputFormType>,
    onClickAddConfirm: (RegisterableRssGroup, FormType) -> Unit,
    modifier: Modifier = Modifier,
) {

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.background
            )
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        var expandedFormIndex: Int by remember { mutableIntStateOf(-1) }
        var inputValue: String by remember { mutableStateOf("") }

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Image(
                painter = painterResource(target.iconRes),
                contentDescription = null,
                modifier = Modifier
                    .size(36.dp)
                    .clip(
                        shape = MaterialTheme.shapes.medium
                    )
            )

            Column {

                Text(
                    text = target.displayName,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onBackground
                )

                Text(
                    text = target.description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
                )
            }
        }

        HorizontalDivider()

        Column(
            modifier = Modifier
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            if (inputForms.isEmpty()) {

                Text(
                    text = "これ以上RSSを追加することができません",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
                )
            } else {

                inputForms.forEachIndexed { index, item ->
                    RssInputForm(
                        model = item,
                        isExpanded = (index == expandedFormIndex),
                        inputValue = inputValue,
                        onClick = {
                            inputValue = ""
                            expandedFormIndex = if (index == expandedFormIndex) {
                                -1
                            } else {
                                index
                            }
                        },
                        onValueChange = { value ->
                            inputValue = value
                        }
                    )
                }
            }
        }

        Button(
            onClick = {
                val formType = when (inputForms.getOrNull(expandedFormIndex)) {
                    RssInputFormType.USER -> UserId.of(inputValue)
                    RssInputFormType.TAG -> Tag.of(inputValue)
                    RssInputFormType.URL -> URL.of(inputValue)
                    RssInputFormType.POPULAR -> Popular
                    null -> throw IllegalArgumentException("invalid form type")
                }
                onClickAddConfirm(target, formType)
            },
            enabled = inputValue.isNotBlank(),
            modifier = Modifier
                .align(Alignment.End)
        ) {
            Text(text = "追加する")
        }

        Spacer(
            modifier = Modifier
                .height(32.dp)
        )
    }
}

@Preview
@Composable
private fun RSSAdditionalFormContentPreview() {
    SmartRssTheme {
        RSSAdditionalFormContent(
            target = RegisterableRssGroup.Qiita,
            inputForms = RssInputFormTypePreviewParameterProvider().values.toList(),
            onClickAddConfirm = { _, _ -> },
        )
    }
}

@Preview
@Composable
private fun RSSAdditionalFormContentPreview_Empty() {
    SmartRssTheme {
        RSSAdditionalFormContent(
            target = RegisterableRssGroup.Qiita,
            inputForms = emptyList(),
            onClickAddConfirm = { _, _ -> },
        )
    }
}
