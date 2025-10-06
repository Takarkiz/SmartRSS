package com.khaki.smartrss.ext

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.number
import kotlinx.datetime.toLocalDateTime
import kotlin.time.ExperimentalTime

/**
 * LocalDateTime を日本語の相対表現文字列に変換します。
 *
 * - 今日: 「今日 H:mm」
 * - 昨日: 「昨日 H:mm」
 * - 同年のそれ以前: 「M月d日 H:mm」
 * - 年が異なる: 「yyyy年M月d日 H:mm」
 */
@OptIn(ExperimentalTime::class)
fun LocalDateTime.toRelativeJaString(): String {
    val zone = TimeZone.currentSystemDefault()
    val nowDate = kotlin.time.Clock.System.now().toLocalDateTime(zone).date
    val d = this.date
    val yesterday = LocalDate.fromEpochDays(nowDate.toEpochDays() - 1)
    val time = "${this.hour}:${this.minute.toString().padStart(2, '0')}"
    return when {
        d == nowDate -> "今日 $time"
        d == yesterday -> "昨日 $time"
        d.year == nowDate.year -> "${month.number}月${day}日 $time"
        else -> "${this.year}年${month.number}月${day}日 $time"
    }
}
