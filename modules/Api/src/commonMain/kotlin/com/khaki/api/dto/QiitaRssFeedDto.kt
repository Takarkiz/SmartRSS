package com.khaki.api.dto

import kotlinx.serialization.Serializable
import nl.adaptivity.xmlutil.serialization.XmlElement
import nl.adaptivity.xmlutil.serialization.XmlSerialName
import nl.adaptivity.xmlutil.serialization.XmlValue

private const val ATOM_NS = "http://www.w3.org/2005/Atom"

@Serializable
@XmlSerialName("feed", namespace = ATOM_NS)
data class QiitaRssFeedDto(
    val title: String,
    val link: List<Link>,
    val entry: List<QiitaRssEntryDto>
) {
    @Serializable
    @XmlSerialName("entry", namespace = ATOM_NS)
    data class QiitaRssEntryDto(
        @XmlSerialName("id") val articleId: String,
        val title: String,
        val link: List<Link>,
        @XmlSerialName("published") val pubDate: String,
        val author: Author,
        @XmlSerialName("content") val description: Description
    )

    @Serializable
    @XmlSerialName(value = "link", namespace = ATOM_NS)
    data class Link(
        @XmlElement val rel: String, // 例: "alternate", "self"
        @XmlElement val type: String, // 例: "text/html", "application/atom+xml"
        @XmlElement val href: String // URL
    )

    @Serializable
    @XmlSerialName("content", namespace = ATOM_NS)
    data class Description(
        @XmlElement val type: String,
        @XmlValue val value: String
    )

    @Serializable
    @XmlSerialName("author", namespace = ATOM_NS)
    data class Author(
        val name: String
    )

}
