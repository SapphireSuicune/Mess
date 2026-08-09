package com.retrosquare.mess

import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.toLocalDateTime
import kotlinx.datetime.format.MonthNames
import kotlinx.datetime.format.Padding
import kotlinx.datetime.format.char
import kotlinx.datetime.format
import android.content.Context
import android.content.Intent
import android.graphics.Canvas
import android.graphics.Paint
import androidx.core.graphics.createBitmap
import androidx.core.graphics.drawable.IconCompat

fun parseTimestamp(ts: Long): String {
    val instant = Instant.fromEpochMilliseconds(ts)
    val ldt = instant.toLocalDateTime(TimeZone.currentSystemDefault())

    val tsFormat = LocalDateTime.Format {
        monthName(MonthNames.ENGLISH_FULL)
            char(' ')
        dayOfMonth(padding = Padding.NONE)
            chars(", ")
        year()
            chars(" @ ")
        amPmHour(padding = Padding.NONE)
            char(':')
        minute(padding = Padding.ZERO)
            char(' ')
        amPmMarker(am = "AM", pm = "PM")
    }

    // July 3, 2026 @ 8:01 PM
    
    return ldt.format(tsFormat)
}

fun shareMess(ctx: Context, mess: Mess) {
    val text = """
        ${ parseTimestamp(mess.timestamp) }
        ${ mess.sender } said:
        "${ mess.message }"

        Sent from Mess App
    """.trimIndent()

    val sendIntent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_TEXT, text)
    }
    ctx.startActivity(Intent.createChooser(sendIntent, null))
}

fun initialsIcon(sender: String): IconCompat {
    val size = 128
    val bmp = createBitmap(size, size)
    val canvas = Canvas(bmp)

    val colors = listOf(0xFFE57373, 0xFF81C784, 0xFF64B5F6, 0xFFFFB74D, 0xFFBA68C8, 0xFF4DB6AC)
    val color = colors[Math.floorMod(sender.hashCode(), colors.size)].toInt()

    canvas.drawColor(color)
    Paint().apply {
        this.color = android.graphics.Color.WHITE
        textSize = size * 0.5f
        textAlign = Paint.Align.CENTER
        isAntiAlias = true
        val y = size / 2f - (descent() + ascent()) / 2f
        canvas.drawText(sender.first().uppercase(), size / 2f, y, this)
    }

    return IconCompat.createWithAdaptiveBitmap(bmp)
}
