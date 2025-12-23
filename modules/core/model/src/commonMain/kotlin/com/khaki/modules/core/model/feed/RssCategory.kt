package com.khaki.modules.core.model.feed

data class RssCategory(
    val id: String,
    val name: String,
    val description: String?,
    val following: Boolean,
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

    fun getFeedUrl(): String {
        return when (type) {
            RSSGroupType.Qiita -> {
                when (formType) {
                    is Tag -> "https://qiita.com/tags/${formType.value}/feed.atom"
                    is UserId -> "https://qiita.com/${formType.value}/feed.atom"
                    is Popular -> "https://qiita.com/popular-items/feed.atom"
                    else -> throw IllegalStateException()
                }
            }

            RSSGroupType.Zenn -> {
                when (formType) {
                    is Tag -> "https://zenn.dev/topics/${formType.value}/feed"
                    is UserId -> "https://zenn.dev/${formType.value}/feed"
                    is Popular -> "https://zenn.dev/feed"
                    else -> throw IllegalStateException()
                }
            }

            RSSGroupType.HatenaBlog -> {
                when (formType) {
                    is URL -> {
                        val url = formType.value.removeSuffix("/")
                        if (url.endsWith("/feed")) {
                            url
                        } else {
                            "$url/feed"
                        }
                    }
                    else -> throw IllegalStateException()
                }
            }

            RSSGroupType.Github -> {
                throw IllegalStateException()
            }

            RSSGroupType.Others -> {
                when (formType) {
                    is URL -> formType.value
                    else -> throw IllegalStateException()
                }
            }
        }
    }
}
