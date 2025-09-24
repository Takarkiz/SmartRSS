package com.khaki.repositoryimpl

import com.khaki.modules.core.model.feed.Popular
import com.khaki.modules.core.model.feed.Tag
import com.khaki.modules.core.model.feed.URL
import com.khaki.modules.core.model.feed.UserId
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs

class RssUrlDeserializerTest {

    @Test
    fun popular_feed_https() {
        val input = "https://qiita.com/popular-items/feed.atom"
        val result = RssUrlDeserializer.fromQiitaUrl(input)
        assertIs<Popular>(result)
    }

    @Test
    fun popular_feed_http_with_trailing_slash_and_spaces() {
        val input = "  http://qiita.com/popular-items/feed.atom/  "
        val result = RssUrlDeserializer.fromQiitaUrl(input)
        assertIs<Popular>(result)
    }

    @Test
    fun tag_feed_returns_Tag_value() {
        val input = "http://qiita.com/tags/kotlin/feed.atom"
        val result = RssUrlDeserializer.fromQiitaUrl(input)
        val tag = assertIs<Tag>(result)
        assertEquals("kotlin", tag.value)
    }

    @Test
    fun user_feed_returns_UserId_value() {
        val input = "https://qiita.com/alice/feed.atom"
        val result = RssUrlDeserializer.fromQiitaUrl(input)
        val user = assertIs<UserId>(result)
        assertEquals("alice", user.value)
    }

    @Test
    fun unmatched_url_falls_back_to_URL() {
        val input = "https://example.com/feed"
        val result = RssUrlDeserializer.fromQiitaUrl(input)
        val url = assertIs<URL>(result)
        assertEquals("https://example.com/feed", url.value)
    }

    @Test
    fun unmatched_qiita_path_falls_back_to_URL() {
        val input = "https://qiita.com/some/path/not/feed"
        val result = RssUrlDeserializer.fromQiitaUrl(input)
        val url = assertIs<URL>(result)
        assertEquals(input, url.value)
    }
}
