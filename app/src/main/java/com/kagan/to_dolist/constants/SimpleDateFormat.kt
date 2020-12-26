package com.kagan.to_dolist.constants

import java.text.SimpleDateFormat
import java.util.*

object SimpleDateFormat {

    fun formatTime(dateTime: Long): String {
        return SimpleDateFormat("HH:mm", Locale.UK).format(dateTime)
    }

    fun formatDate(dateTime: Long): String {
        return SimpleDateFormat("yyyy-MM-dd", Locale.UK).format(dateTime)
    }
}