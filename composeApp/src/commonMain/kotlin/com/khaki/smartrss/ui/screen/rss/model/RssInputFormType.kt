package com.khaki.smartrss.ui.screen.rss.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Link
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Tag
import androidx.compose.ui.graphics.vector.ImageVector
import org.jetbrains.compose.ui.tooling.preview.PreviewParameterProvider

/**
 * 入力種別
 */
enum class RssInputFormType(
    val title: String,
    val iconImageVector: ImageVector,
) {

    POPULAR(
        title = "人気記事の登録",
        iconImageVector = Icons.Default.Star
    ),

    USER(
        title = "ユーザーIDで登録",
        iconImageVector = Icons.Default.Person
    ),

    TAG(
        title = "タグで登録",
        iconImageVector = Icons.Default.Tag
    ),

    URL(
        title = "URLで登録",
        iconImageVector = Icons.Default.Link
    );

    companion object {
        fun supportedFor(group: RegisterableRssGroup): List<RssInputFormType> {
            return when (group) {
                RegisterableRssGroup.Qiita -> listOf(POPULAR, USER, TAG)
                RegisterableRssGroup.Zenn -> listOf(POPULAR, USER, TAG)
                RegisterableRssGroup.HatenaBlog -> listOf(URL)
                RegisterableRssGroup.Github -> listOf(URL) // Adjust as needed
                RegisterableRssGroup.Others -> listOf(URL)
            }
        }
    }
}

internal class RssInputFormTypePreviewParameterProvider :
    PreviewParameterProvider<RssInputFormType> {
    override val values: Sequence<RssInputFormType>
        get() = sequenceOf(
            RssInputFormType.POPULAR,
            RssInputFormType.USER,
            RssInputFormType.TAG,
            RssInputFormType.URL,
        )
}
