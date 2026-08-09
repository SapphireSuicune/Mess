package com.retrosquare.mess

import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.toLocalDateTime
import kotlinx.datetime.format.MonthNames
import kotlinx.datetime.format.Padding
import kotlinx.datetime.format.char
import kotlinx.datetime.format

fun parseTimestamp(ts: Long): String {
    val instant = Instant.fromEpochMilliseconds(ts)
    val ldt = instant.toLocalDateTime(TimeZone.currentSystemDefault())

    val tsFormat = LocalDateTime.Format {
        monthName(MonthNames.ENGLISH_FULL)
            char(' ')
        dayOfMonth(padding = Padding.NONE)
            chars(", ")
        year()
            chars(" | ")
        amPmHour(padding = Padding.NONE)
            char(':')
        minute(padding = Padding.ZERO)
            char(' ')
        amPmMarker(am = "AM", pm = "PM")
    }

    // July 3, 2026 | 8:01 PM
    
    return ldt.format(tsFormat)
}