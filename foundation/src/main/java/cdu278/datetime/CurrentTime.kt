package cdu278.datetime

import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

fun Clock.currentTime(timeZone: TimeZone = TimeZone.currentSystemDefault()): LocalDateTime {
    return now().toLocalDateTime(timeZone)
}