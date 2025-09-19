package com.khaki.smartrss.ui.screen.rss.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import com.khaki.smartrss.ui.screen.rss.model.RegisterableRssGroup
import com.khaki.smartrss.ui.screen.rss.model.RssInputFormType
import com.khaki.smartrss.ui.screen.rss.model.RssInputFormTypePreviewParameterProvider
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import smartrss.composeapp.generated.resources.Res
import smartrss.composeapp.generated.resources.compose_multiplatform

@Composable
internal fun RSSAdditionalFormContent(
    target: RegisterableRssGroup,
    inputForms: List<RssInputFormType>,
    modifier: Modifier = Modifier,
) {

    when (target) {
        RegisterableRssGroup.Qiita -> {
            // Qiita用の追加フォームコンテンツをここに追加
        }

        RegisterableRssGroup.Zenn -> {
            // Zenn用の追加フォームコンテンツをここに追加
        }

        RegisterableRssGroup.HatenaBlog -> {
            // はてなブログ用の追加フォームコンテンツをここに追加
        }

        RegisterableRssGroup.Github -> {
            // GitHubリリースノート用の追加フォームコンテンツをここに追加
        }

        RegisterableRssGroup.Others -> {
            // カスタムRSS用の追加フォームコンテンツをここに追加
        }
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.background
            )
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Image(
                painter = painterResource(Res.drawable.compose_multiplatform),
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
                    text = "技術記事共有サイト",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
                )
            }
        }

        HorizontalDivider()

        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {

            var expandedFormIndex: Int by remember { mutableIntStateOf(-1) }
            var inputValue: String by remember { mutableStateOf("") }

            inputForms.forEachIndexed { index, item ->
                RssInputForm(
                    model = item,
                    isExpanded = (index == expandedFormIndex),
                    inputValue = inputValue,
                    onClick = {
                        if (index == expandedFormIndex) {
                            expandedFormIndex = -1
                        } else {
                            expandedFormIndex = index
                            inputValue = ""
                        }
                    },
                    onValueChange = { value ->
                        inputValue = value
                    }
                )
            }
        }
    }
}

@Preview
@Composable
private fun RSSAdditionalFormContentPreview() {
    RSSAdditionalFormContent(
        target = RegisterableRssGroup.Qiita,
        inputForms = RssInputFormTypePreviewParameterProvider().values.toList()
    )
}
