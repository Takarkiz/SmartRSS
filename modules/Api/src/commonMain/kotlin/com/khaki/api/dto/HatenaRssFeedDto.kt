package com.khaki.api.dto

import kotlinx.serialization.Serializable
import nl.adaptivity.xmlutil.serialization.XmlElement
import nl.adaptivity.xmlutil.serialization.XmlSerialName
import nl.adaptivity.xmlutil.serialization.XmlValue

private const val ATOM_NS = "http://www.w3.org/2005/Atom"

@Serializable
@XmlSerialName("feed", namespace = ATOM_NS)
data class HatenaRssFeedDto(
    @XmlElement(true)
    @XmlSerialName("id", namespace = ATOM_NS)
    val id: String,

    @XmlElement(true)
    @XmlSerialName("title", namespace = ATOM_NS)
    val title: String,

    @XmlElement(true)
    @XmlSerialName("subtitle", namespace = ATOM_NS)
    val subtitle: String? = null,

    @XmlElement(true)
    @XmlSerialName("link", namespace = ATOM_NS)
    val links: List<Link> = emptyList(),

    @XmlElement(true)
    @XmlSerialName("updated", namespace = ATOM_NS)
    val updated: String,

    @XmlElement(true)
    @XmlSerialName("author", namespace = ATOM_NS)
    val author: Author? = null,

    @XmlElement(true)
    @XmlSerialName("generator", namespace = ATOM_NS)
    val generator: Generator? = null,

    @XmlElement(true)
    @XmlSerialName("entry", namespace = ATOM_NS)
    val entries: List<Entry> = emptyList()
) {

    @Serializable
    data class Link(
        @XmlElement(false)
        @XmlSerialName("rel")
        val rel: String? = null,

        @XmlElement(false)
        @XmlSerialName("type")
        val type: String? = null,

        @XmlElement(false)
        @XmlSerialName("href")
        val href: String? = null,

        @XmlElement(false)
        @XmlSerialName("length")
        val length: String? = null,

        @XmlValue
        val url: String? = null
    )

    @Serializable
    data class Author(
        @XmlElement(true)
        @XmlSerialName("name", namespace = ATOM_NS)
        val name: String
    )

    @Serializable
    data class Generator(
        @XmlElement(false)
        @XmlSerialName("uri")
        val uri: String? = null,

        @XmlElement(false)
        @XmlSerialName("version")
        val version: String? = null,

        @XmlValue
        val value: String? = null
    )

    @Serializable
    data class Entry(
        @XmlElement(true)
        @XmlSerialName("title", namespace = ATOM_NS)
        val title: String,

        // Hatena entries can contain multiple link elements (e.g., main link and enclosure)
        @XmlElement(true)
        @XmlSerialName("link", namespace = ATOM_NS)
        val links: List<Link> = emptyList(),

        @XmlElement(true)
        @XmlSerialName("id", namespace = ATOM_NS)
        val id: String,

        @XmlElement(true)
        @XmlSerialName("published", namespace = ATOM_NS)
        val published: String? = null,

        @XmlElement(true)
        @XmlSerialName("updated", namespace = ATOM_NS)
        val updated: String? = null,

        @XmlElement(true)
        @XmlSerialName("summary", namespace = ATOM_NS)
        val summary: Summary? = null,

        @XmlElement(true)
        @XmlSerialName("content", namespace = ATOM_NS)
        val content: Content? = null,

        @XmlElement(true)
        @XmlSerialName("author", namespace = ATOM_NS)
        val author: Author? = null
    )

    @Serializable
    data class Summary(
        @XmlElement(false)
        @XmlSerialName("type")
        val type: String? = null,

        @XmlValue
        val value: String? = null
    )

    @Serializable
    data class Content(
        @XmlElement(false)
        @XmlSerialName("type")
        val type: String? = null,

        @XmlValue
        val value: String? = null
    )
}
