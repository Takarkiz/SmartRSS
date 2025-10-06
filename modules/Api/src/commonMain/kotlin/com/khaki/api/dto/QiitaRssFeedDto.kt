package com.khaki.api.dto

import kotlinx.serialization.Serializable
import nl.adaptivity.xmlutil.serialization.XmlElement
import nl.adaptivity.xmlutil.serialization.XmlSerialName
import nl.adaptivity.xmlutil.serialization.XmlValue

private const val ATOM_NS = "http://www.w3.org/2005/Atom"

@Serializable
@XmlSerialName("feed", namespace = ATOM_NS)
data class QiitaRssFeedDto(
    @XmlElement(true)
    @XmlSerialName("id", namespace = ATOM_NS)
    val id: String,

    @XmlElement(true)
    @XmlSerialName("link", namespace = ATOM_NS)
    val links: List<Link>,

    @XmlElement(true)
    @XmlSerialName("title", namespace = ATOM_NS)
    val title: String,

    @XmlElement(true)
    @XmlSerialName("description", namespace = ATOM_NS)
    val description: String? = null,

    @XmlElement(true)
    @XmlSerialName("updated", namespace = ATOM_NS)
    val updated: String,

    @XmlElement(true)
    @XmlSerialName("entry", namespace = ATOM_NS)
    val entries: List<QiitaRssEntryDto>
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

        @XmlValue
        val url: String? = null
    )

    @Serializable
    data class QiitaRssEntryDto(
        @XmlElement(true)
        @XmlSerialName("id", namespace = ATOM_NS)
        val articleId: String,

        @XmlElement(true)
        @XmlSerialName("published", namespace = ATOM_NS)
        val pubDate: String,

        @XmlElement(true)
        @XmlSerialName("updated", namespace = ATOM_NS)
        val updatedDate: String,

        @XmlElement(true)
        @XmlSerialName("link", namespace = ATOM_NS)
        val link: Link,

        @XmlElement(true)
        @XmlSerialName("url", namespace = ATOM_NS)
        val url: String? = null,

        @XmlElement(true)
        @XmlSerialName("title", namespace = ATOM_NS)
        val title: String,

        @XmlElement(true)
        @XmlSerialName("content", namespace = ATOM_NS)
        val content: Content,

        @XmlElement(true)
        @XmlSerialName("author", namespace = ATOM_NS)
        val author: Author
    )

    @Serializable
    data class Content(
        @XmlElement(false)
        @XmlSerialName("type")
        val type: String,

        @XmlValue
        val value: String
    )

    @Serializable
    data class Author(
        @XmlElement(true)
        @XmlSerialName("name", namespace = ATOM_NS)
        val name: String
    )
}
