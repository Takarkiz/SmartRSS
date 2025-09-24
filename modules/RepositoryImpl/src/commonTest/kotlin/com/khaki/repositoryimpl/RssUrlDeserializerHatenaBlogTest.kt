package com.khaki.repositoryimpl

import com.khaki.modules.core.model.feed.URL
import com.khaki.modules.core.model.feed.UserId
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs

class RssUrlDeserializerHatenaBlogTest {

    @Test
    fun user_feed_https() {
        val input = "https://exampleblog.hatenadiary.jp/feed"
        val result = RssUrlDeserializer.fromHatenaBlogUrl(input)
        val user = assertIs<UserId>(result)
        assertEquals("exampleblog", user.value)
    }

    @Test
    fun user_feed_http_trailing_slash_and_spaces() {
        val input = "  http://my-blog-123.hatenadiary.jp/feed/  "
        val result = RssUrlDeserializer.fromHatenaBlogUrl(input)
        val user = assertIs<UserId>(result)
        assertEquals("my-blog-123", user.value)
    }

    @Test
    fun unmatched_no_subdomain_falls_back_to_URL() {
        val input = "https://hatenadiary.jp/feed"
        val result = RssUrlDeserializer.fromHatenaBlogUrl(input)
        val url = assertIs<URL>(result)
        assertEquals(input, url.value)
    }

    @Test
    fun unmatched_wrong_path_falls_back_to_URL() {
        val input = "https://foo.hatenadiary.jp/entry/feed"
        val result = RssUrlDeserializer.fromHatenaBlogUrl(input)
        val url = assertIs<URL>(result)
        assertEquals(input, url.value)
    }
}
