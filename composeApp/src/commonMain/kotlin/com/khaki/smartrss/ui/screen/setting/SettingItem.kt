package com.khaki.smartrss.ui.screen.setting

enum class SettingItem(
    val category: SettingCategory,
    val type: SettingType,
    val title: String,
    val subTitle: String?,
) {

    FEED_LIST_SUMMARY(
        category = SettingCategory.FEED_LIST,
        type = SettingType.SWITCH,
        title = "AIによる要約を概要に表示する",
        subTitle = "記事の概要を自動生成します",
    ),
    DELETE_ALL_FEEDS(
        category = SettingCategory.FEED_LIST,
        type = SettingType.BUTTON,
        title = "フィードを全て削除",
        subTitle = null,
    )
}

enum class SettingCategory(val title: String) {
    FEED_LIST("フィード一覧");
}

enum class SettingType {
    SWITCH,
    BUTTON
}
