package com.khaki.modules.core.model.feed

import kotlin.jvm.JvmInline

interface FormType

/**
 * タグによるフィードの分類を表す値オブジェクト
 */
@JvmInline
value class Tag(
    val value: String,
) : FormType {

    companion object {

        fun of(rawValue: String) = Tag(
            value = rawValue.trim()
        )
    }
}

/**
 * ユーザーIDを表す値オブジェクト
 */
@JvmInline
value class UserId(
    val value: String,
) : FormType {

    companion object {

        fun of(rawValue: String) = UserId(
            value = rawValue.trim().removePrefix("@")
        )
    }
}

/**
 * URLを表す値オブジェクト
 */
@JvmInline
value class URL(
    val value: String,
) : FormType {

    companion object {

        fun of(rawValue: String) = URL(
            value = rawValue.trim()
        )
    }

    private fun isValidUrl(): Boolean {
        // Java に依存しない簡易 URL バリデーション（KMP 対応）
        // http/https スキーム、ドメイン（例: example.com）、IPv4 を許容。localhost は許容しない。
        // ポート、パス、クエリ、フラグメントは任意。
        // コメントを使わない簡潔な正規表現（IGNORE_CASE のみ使用）
        val urlPattern = Regex(
            pattern = "^https?://(?:(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,}|\\d{1,3}(?:\\.\\d{1,3}){3})(?::\\d{1,5})?(?:/[^?\\s#]*)?(?:\\?[^#\\s]*)?(?:#[^\\s]*)?$",
            options = setOf(RegexOption.IGNORE_CASE)
        )
        return urlPattern.matches(value.trim())
    }
}

object Popular : FormType
