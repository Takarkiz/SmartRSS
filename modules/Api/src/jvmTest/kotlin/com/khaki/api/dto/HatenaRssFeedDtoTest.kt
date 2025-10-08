package com.khaki.api.dto

import nl.adaptivity.xmlutil.serialization.XML
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class HatenaRssFeedDtoTest {

    @Test
    fun parse_hatena_feed_sample() {
        val xmlText = readResource("hatena_sample.xml")
        val parsed = XML().decodeFromString(HatenaRssFeedDto.serializer(), xmlText)

        // feed level
        assertEquals("hatenablog://blog/1234567890", parsed.id)
        assertEquals("My Test Blog", parsed.title)
        assertEquals("test-user", parsed.author?.name)
        assertEquals("https://blog.hatena.ne.jp/", parsed.generator?.uri)
        assertEquals("abcdef1234567890", parsed.generator?.version)
        assertEquals("Hatena::Blog", parsed.generator?.value)
        assertTrue(parsed.entries.isNotEmpty(), "entries should not be empty")

        // entry level (first one)
        val first = parsed.entries.first()
        assertEquals("Test Entry Title 1", first.title)
        assertEquals("hatenablog://entry/9876543211", first.id)
        assertNotNull(first.published)
        assertNotNull(first.updated)
        assertNotNull(first.summary)
        assertNotNull(first.content)

        // links include main href and enclosure
        assertTrue(first.links.any { it.href?.contains("/entry/") == true })
        assertTrue(first.links.any { it.rel == "enclosure" && (it.type?.startsWith("image") == true) })
    }

    private fun readResource(name: String): String {
        val cl = this::class.java.classLoader ?: error("ClassLoader not found")
        cl.getResourceAsStream(name).use { stream ->
            requireNotNull(stream) { "Resource $name not found" }
            return stream.reader(Charsets.UTF_8).readText()
        }
    }
}
