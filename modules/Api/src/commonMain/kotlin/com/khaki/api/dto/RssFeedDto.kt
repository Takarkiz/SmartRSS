package com.khaki.api.dto

import kotlinx.serialization.Serializable
import nl.adaptivity.xmlutil.serialization.XmlElement
import nl.adaptivity.xmlutil.serialization.XmlSerialName

private const val CONTENT_NS = "http://purl.org/rss/1.0/modules/content/"
private const val ATOM_NS = "http://www.w3.org/2005/Atom"

@Serializable
@XmlSerialName("rss")
data class RssFeedDto(
    @XmlElement(false)
    @XmlSerialName("version")
    val version: String,

    @XmlElement(true)
    @XmlSerialName("channel")
    val channel: ChannelDto
) {
    @Serializable
    @XmlSerialName("channel")
    data class ChannelDto(
        @XmlElement(true)
        @XmlSerialName("title")
        val title: String,

        @XmlElement(true)
        @XmlSerialName("link")
        val link: String,

        @XmlElement(true)
        @XmlSerialName("description")
        val description: String,

        @XmlElement(true)
        @XmlSerialName("lastBuildDate")
        val lastBuildDate: String,

        @XmlElement(true)
        @XmlSerialName("docs")
        val docs: String,

        @XmlElement(true)
        @XmlSerialName("generator")
        val generator: String,

        @XmlElement(true)
        @XmlSerialName("language")
        val language: String,

        @XmlElement(true)
        @XmlSerialName("image")
        val image: ImageDto? = null,

        @XmlElement(true)
        @XmlSerialName("copyright")
        val copyright: String,

        @XmlElement(true)
        @XmlSerialName("link", namespace = ATOM_NS)
        val atomLink: AtomLinkDto,

        @XmlElement(true)
        @XmlSerialName("item")
        val items: List<ItemDto>
    )


    @Serializable
    @XmlSerialName("image")
    data class ImageDto(
        @XmlElement(true)
        @XmlSerialName("title")
        val title: String,

        @XmlElement(true)
        @XmlSerialName("url")
        val url: String,

        @XmlElement(true)
        @XmlSerialName("link")
        val link: String
    )

    @Serializable
    @XmlSerialName("link", namespace = ATOM_NS)
    data class AtomLinkDto(
        @XmlElement(false)
        @XmlSerialName("href")
        val href: String,

        @XmlElement(false)
        @XmlSerialName("rel")
        val rel: String,

        @XmlElement(false)
        @XmlSerialName("type")
        val type: String
    )

    @Serializable
    @XmlSerialName("item")
    data class ItemDto(
        @XmlElement(true)
        @XmlSerialName("title")
        val title: String,

        @XmlElement(true)
        @XmlSerialName("link")
        val link: String,

        @XmlElement(true)
        @XmlSerialName("guid")
        val guid: String,

        @XmlElement(true)
        @XmlSerialName("pubDate")
        val pubDate: String,

        @XmlElement(true)
        @XmlSerialName("description")
        val description: String,

        @XmlElement(true)
        @XmlSerialName("encoded", namespace = CONTENT_NS)
        val contentEncoded: String,

        @XmlElement(true)
        @XmlSerialName("enclosure")
        val enclosure: EnclosureDto? = null
    )

    @Serializable
    @XmlSerialName("enclosure")
    data class EnclosureDto(
        @XmlElement(false)
        @XmlSerialName("length")
        val length: String,

        @XmlElement(false)
        @XmlSerialName("type")
        val type: String,

        @XmlElement(false)
        @XmlSerialName("url")
        val url: String,

        @XmlElement(false)
        @XmlSerialName("alt")
        val alt: String? = null
    )
}
