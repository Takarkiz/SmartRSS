package com.khaki.api.dto

import nl.adaptivity.xmlutil.serialization.XML
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class RssFeedDtoTest {

    @Test
    fun parse_rss_feed_sample() {
        val xmlText = readResource("rss_feeds_sample.xml")
        val parsed = XML {
            defaultPolicy {
                ignoreUnknownChildren()
            }
        }.decodeFromString(RssFeedDto.serializer(), xmlText)

        // RSS level
        assertEquals("2.0", parsed.version)

        // Channel level
        val channel = parsed.channel
        assertEquals("サンプルRSS", channel.title)
        assertEquals("https://example.com/", channel.link)
        assertEquals("これはサンプルのRSSフィードです", channel.description)
        assertNotNull(channel.lastBuildDate)
        assertEquals("https://validator.w3.org/feed/docs/rss2.html", channel.docs)
        assertEquals("sample-generator", channel.generator)
        assertEquals("ja", channel.language)
        assertEquals("sample-copyright", channel.copyright)
        assertEquals("https://example.com/feeds/rss.xml", channel.atomLink?.href)

        // Image level
        val image = channel.image
        assertNotNull(image)
        assertEquals("サンプルRSS", image.title)
        assertEquals("https://example.com/images/icon.png", image.url)
        assertEquals("https://example.com/", image.link)

        // Item level (first one)
        assertTrue(channel.items.isNotEmpty(), "items should not be empty")
        val firstItem = channel.items.first()
        assertEquals("サンプル記事1", firstItem.title.trim())
        assertEquals("https://example.com/articles/1", firstItem.link)
        assertEquals("https://example.com/articles/1", firstItem.guid)
        assertNotNull(firstItem.pubDate)
        assertNotNull(firstItem.description)
        assertTrue(firstItem.description.trim().startsWith("これはサンプルの説明1"))
        assertNotNull(firstItem.contentEncoded)
        assertTrue(firstItem.contentEncoded.trim().startsWith("これはサンプルの本文1"))

        val enclosure = firstItem.enclosure
        assertNotNull(enclosure)
        assertEquals("0", enclosure.length)
        assertEquals("png", enclosure.type)
        assertTrue(enclosure.url.startsWith("https://example.com/images/"))
    }

    private fun readResource(name: String): String {
        val cl = this::class.java.classLoader ?: error("ClassLoader not found")
        cl.getResourceAsStream(name).use { stream ->
            requireNotNull(stream) { "Resource $name not found" }
            return stream.reader(Charsets.UTF_8).readText()
        }
    }
}
