package com.kagan.to_dolist.constants

import java.text.SimpleDateFormat
import java.util.*

object SimpleDateFormat {

    fun formatTime(dateTime: Long): String {
        return SimpleDateFormat("hh:mm a", Locale.UK).format(dateTime)
    }
}