package com.khaki.smartrss.ui.screen.rss.model

import org.jetbrains.compose.ui.tooling.preview.PreviewParameterProvider

data class RegisteredRssGroup(
    val id: String,
    val name: String,
    val url: String,
    val type: RegisterableRssGroup,
)

internal class RegisteredRssGroupPreviewParameterProvider :
    PreviewParameterProvider<RegisteredRssGroup> {
    override val values: Sequence<RegisteredRssGroup>
        get() = sequenceOf(
            RegisteredRssGroup(
                id = "1",
                name = "Takarkiz の投稿 - Qiita",
                url = "https://example.com/techbloga",
                type = RegisterableRssGroup.Qiita,
            ),
            RegisteredRssGroup(
                id = "2",
                name = "人気の投稿 - Qiita",
                url = "https://example.com/newssiteb",
                type = RegisterableRssGroup.Zenn,
            ),
            RegisteredRssGroup(
                id = "3",
                name = "Compose Framework Docs",
                url = "https://developer.android.com/jetpack/compose",
                type = RegisterableRssGroup.Others,
            )
        )
}
