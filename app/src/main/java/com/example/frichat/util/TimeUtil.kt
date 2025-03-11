package com.example.frichat.util

import android.text.format.DateUtils
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.concurrent.TimeUnit

object TimeUtil {
    fun formatLastUpdateTime(time: Date): String {
        val timestamp = time.time
        val now = System.currentTimeMillis()
        val diff = now - timestamp

        return when {
            diff < TimeUnit.MINUTES.toMillis(1) -> "Just now"
            diff < TimeUnit.HOURS.toMillis(1) -> "${TimeUnit.MILLISECONDS.toMinutes(diff)}m"
            diff < TimeUnit.DAYS.toMillis(1) -> "${TimeUnit.MILLISECONDS.toHours(diff)}h"
            diff < TimeUnit.DAYS.toMillis(7) -> SimpleDateFormat("EEE", Locale.getDefault()).format(time)
            else -> SimpleDateFormat("dd/MM", Locale.getDefault()).format(time)
        }
    }
}