package com.khaki.smartrss.ui.screen.setting.model

internal enum class RegisterableRssGroup(val displayName: String) {
    Qiita("Qiita"),
    Zenn("Zenn"),
    HatenaBlog("はてなブログ"),
    Github("GitHubリリースノート"),
    Others("カスタムRSS"),
}