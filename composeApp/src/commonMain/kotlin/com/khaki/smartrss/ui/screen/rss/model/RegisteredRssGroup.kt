package com.khaki.smartrss.ui.screen.rss.model

import org.jetbrains.compose.ui.tooling.preview.PreviewParameterProvider

data class RegisteredRssGroup(
    val id: String,
    val name: String,
    val url: String,
    val type: RegisterableRssGroup,
    val isEnable: Boolean,
    val faviconUrl: String?,
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
                isEnable = true,
                faviconUrl = "https://example.com/techbloga/favicon.ico"
            ),
            RegisteredRssGroup(
                id = "2",
                name = "人気の投稿 - Qiita",
                url = "https://example.com/newssiteb",
                type = RegisterableRssGroup.Zenn,
                isEnable = false,
                faviconUrl = null
            ),
            RegisteredRssGroup(
                id = "3",
                name = "Compose Framework Docs",
                url = "https://developer.android.com/jetpack/compose",
                type = RegisterableRssGroup.Others,
                isEnable = true,
                faviconUrl = "https://developer.android.com/images/brand/Android_Robot.png"
            )
        )
}
