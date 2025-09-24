package com.khaki.repositoryimpl

import com.khaki.modules.core.model.feed.FormType
import com.khaki.modules.core.model.feed.Popular
import com.khaki.modules.core.model.feed.Tag
import com.khaki.modules.core.model.feed.URL
import com.khaki.modules.core.model.feed.UserId

object RssUrlDeserializer {

    fun fromQiitaUrl(url: String): FormType {
        val input = url.trim()

        // 人気の記事のフィード
        val popularPattern =
            Regex("^https?://qiita\\.com/popular-items/feed\\.atom/?$", RegexOption.IGNORE_CASE)
        if (popularPattern.matches(input)) {
            return Popular
        }

        // 特定のタグの記事フィード
        val tagPattern =
            Regex("^https?://qiita\\.com/tags/([^/]+)/feed\\.atom/?$", RegexOption.IGNORE_CASE)
        val tagMatch = tagPattern.find(input)
        if (tagMatch != null) {
            val tag = tagMatch.groupValues[1]
            return Tag.of(tag)
        }

        // ユーザの記事フィード
        val userPattern =
            Regex("^https?://qiita\\.com/([^/]+)/feed\\.atom/?$", RegexOption.IGNORE_CASE)
        val userMatch = userPattern.find(input)
        if (userMatch != null) {
            val user = userMatch.groupValues[1]
            return UserId.of(user)
        }

        // 上記以外はURLとして扱う
        return URL.of(input)
    }

    fun fromZennUrl(url: String): FormType {
        val input = url.trim()

        // 1. トレンドのフィード
        val popularPattern = Regex("^https?://zenn\\.dev/feed/?$", RegexOption.IGNORE_CASE)
        if (popularPattern.matches(input)) {
            return Popular
        }

        // 3. トピックごとのフィード（topics を先に判定してユーザー名と衝突しないようにする）
        val topicPattern = Regex("^https?://zenn\\.dev/topics/([^/]+)/feed/?$", RegexOption.IGNORE_CASE)
        val topicMatch = topicPattern.find(input)
        if (topicMatch != null) {
            val topic = topicMatch.groupValues[1]
            return Tag.of(topic)
        }

        // 2. ユーザーごとのフィード
        val userPattern = Regex("^https?://zenn\\.dev/([^/]+)/feed/?$", RegexOption.IGNORE_CASE)
        val userMatch = userPattern.find(input)
        if (userMatch != null) {
            val user = userMatch.groupValues[1]
            return UserId.of(user)
        }

        // 該当しない場合は URL
        return URL.of(input)
    }

    fun fromHatenaBlogUrl(url: String): FormType {
        val input = url.trim()

        // はてなブログのユーザーごとのフィード
        // 例: https://{blog-domain}.hatenadiary.jp/feed
        val userPattern = Regex(
            pattern = "^https?://([a-z0-9-]+)\\.hatenadiary\\.jp/feed/?$",
            options = setOf(RegexOption.IGNORE_CASE)
        )
        val match = userPattern.find(input)
        if (match != null) {
            val user = match.groupValues[1]
            return UserId.of(user)
        }

        // 該当しない場合は URL として扱う
        return URL.of(input)
    }
}
