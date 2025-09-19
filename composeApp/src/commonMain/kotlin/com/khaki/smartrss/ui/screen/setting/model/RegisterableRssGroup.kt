package com.khaki.smartrss.ui.screen.setting.model

import org.jetbrains.compose.resources.DrawableResource
import smartrss.composeapp.generated.resources.Res
import smartrss.composeapp.generated.resources.favicon_github
import smartrss.composeapp.generated.resources.favicon_hatena
import smartrss.composeapp.generated.resources.favicon_qiita
import smartrss.composeapp.generated.resources.favicon_rss
import smartrss.composeapp.generated.resources.favicon_zenn

internal enum class RegisterableRssGroup(
    val displayName: String,
    val description: String,
    val iconRes: DrawableResource,

    ) {
    Qiita(
        displayName = "Qiita",
        description = "以下の方法でQiitaの新着記事を取得します",
        iconRes = Res.drawable.favicon_qiita,
    ),
    Zenn(
        "Zenn",
        description = "以下の方法でZennの新着記事を取得します",
        iconRes = Res.drawable.favicon_zenn
    ),
    HatenaBlog(
        displayName = "はてなブログ",
        description = "対象のテックブログIDを指定することで、はてなブログの新着記事を取得します",
        iconRes = Res.drawable.favicon_hatena
    ),
    Github(
        displayName = "GitHubリリースノート",
        description = "対象のGitHubリポジトリを指定することで、リリースノートの新着情報を取得します",
        iconRes = Res.drawable.favicon_github
    ),
    Others(
        displayName = "カスタムRSS",
        description = "任意のRSSフィードURLを指定して、記事を取得します",
        iconRes = Res.drawable.favicon_rss
    ),
}
