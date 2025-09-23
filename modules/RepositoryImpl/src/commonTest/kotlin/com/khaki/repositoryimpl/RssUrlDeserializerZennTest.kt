package com.khaki.repositoryimpl

import com.khaki.modules.core.model.feed.Popular
import com.khaki.modules.core.model.feed.Tag
import com.khaki.modules.core.model.feed.URL
import com.khaki.modules.core.model.feed.UserId
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs

class RssUrlDeserializerZennTest {

    @Test
    fun trend_feed_https() {
        val input = "https://zenn.dev/feed"
        val result = RssUrlDeserializer.fromZennUrl(input)
        assertIs<Popular>(result)
    }

    @Test
    fun trend_feed_http_trailing_slash_and_spaces() {
        val input = "  http://zenn.dev/feed/  "
        val result = RssUrlDeserializer.fromZennUrl(input)
        assertIs<Popular>(result)
    }

    @Test
    fun topic_feed_returns_Tag_value() {
        val input = "https://zenn.dev/topics/kotlin/feed"
        val result = RssUrlDeserializer.fromZennUrl(input)
        val tag = assertIs<Tag>(result)
        assertEquals("kotlin", tag.value)
    }

    @Test
    fun user_feed_returns_UserId_value() {
        val input = "https://zenn.dev/alice/feed"
        val result = RssUrlDeserializer.fromZennUrl(input)
        val user = assertIs<UserId>(result)
        assertEquals("alice", user.value)
    }

    @Test
    fun unmatched_url_falls_back_to_URL() {
        val input = "https://example.com/zenn/feed"
        val result = RssUrlDeserializer.fromZennUrl(input)
        val url = assertIs<URL>(result)
        assertEquals(input.trim(), url.value)
    }

    @Test
    fun unmatched_zenn_path_falls_back_to_URL() {
        val input = "https://zenn.dev/some/path/not/feed"
        val result = RssUrlDeserializer.fromZennUrl(input)
        val url = assertIs<URL>(result)
        assertEquals(input, url.value)
    }
}
