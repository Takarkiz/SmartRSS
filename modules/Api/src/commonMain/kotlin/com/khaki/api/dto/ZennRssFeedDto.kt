package com.khaki.api.dto

import kotlinx.serialization.Serializable
import nl.adaptivity.xmlutil.serialization.XmlElement
import nl.adaptivity.xmlutil.serialization.XmlSerialName
import nl.adaptivity.xmlutil.serialization.XmlValue

private const val ATOM_NS = "http://www.w3.org/2005/Atom"
private const val DC_NS = "http://purl.org/dc/elements/1.1/"

@Serializable
@XmlSerialName("rss")
data class ZennRssFeedDto(
    @XmlElement(false)
    @XmlSerialName("version")
    val version: String? = null,

    @XmlElement(true)
    @XmlSerialName("channel")
    val channel: Channel
) {

    @Serializable
    data class Channel(
        @XmlElement(true)
        @XmlSerialName("title")
        val title: String,

        @XmlElement(true)
        @XmlSerialName("description")
        val description: String,

        @XmlElement(true)
        @XmlSerialName("link")
        val link: String,

        @XmlElement(true)
        @XmlSerialName("image")
        val image: Image? = null,

        @XmlElement(true)
        @XmlSerialName("generator")
        val generator: String? = null,

        @XmlElement(true)
        @XmlSerialName("lastBuildDate")
        val lastBuildDate: String? = null,

        @XmlElement(true)
        @XmlSerialName("link", namespace = ATOM_NS)
        val atomLinks: List<AtomLink> = emptyList(),

        @XmlElement(true)
        @XmlSerialName("language")
        val language: String? = null,

        @XmlElement(true)
        @XmlSerialName("item")
        val items: List<Item> = emptyList()
    )

    @Serializable
    data class Image(
        @XmlElement(true)
        @XmlSerialName("url")
        val url: String,

        @XmlElement(true)
        @XmlSerialName("title")
        val title: String,

        @XmlElement(true)
        @XmlSerialName("link")
        val link: String
    )

    @Serializable
    data class AtomLink(
        @XmlElement(false)
        @XmlSerialName("href")
        val href: String? = null,

        @XmlElement(false)
        @XmlSerialName("rel")
        val rel: String? = null,

        @XmlElement(false)
        @XmlSerialName("type")
        val type: String? = null
    )

    @Serializable
    data class Item(
        @XmlElement(true)
        @XmlSerialName("title")
        val title: String,

        @XmlElement(true)
        @XmlSerialName("description")
        val description: String,

        @XmlElement(true)
        @XmlSerialName("link")
        val link: String,

        @XmlElement(true)
        @XmlSerialName("guid")
        val guid: Guid? = null,

        @XmlElement(true)
        @XmlSerialName("pubDate")
        val pubDate: String,

        @XmlElement(true)
        @XmlSerialName("enclosure")
        val enclosure: Enclosure? = null,

        @XmlElement(true)
        @XmlSerialName("creator", namespace = DC_NS)
        val creator: String? = null
    )

    @Serializable
    data class Guid(
        @XmlElement(false)
        @XmlSerialName("isPermaLink")
        val isPermaLink: String? = null,

        @XmlValue
        val value: String? = null
    )

    @Serializable
    data class Enclosure(
        @XmlElement(false)
        @XmlSerialName("url")
        val url: String? = null,

        @XmlElement(false)
        @XmlSerialName("length")
        val length: String? = null,

        @XmlElement(false)
        @XmlSerialName("type")
        val type: String? = null
    )
}
