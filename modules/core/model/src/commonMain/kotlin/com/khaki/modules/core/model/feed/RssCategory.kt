package com.khaki.modules.core.model.feed

data class RssCategory(
    val id: String,
    val name: String,
    val description: String?,
    val following: Boolean,
    val url: String,
    val type: RSSGroupType,
    val formType: FormType,
) {
    enum class RSSGroupType {
        Qiita,
        Zenn,
        HatenaBlog,
        Github,
        Others,
    }
}
