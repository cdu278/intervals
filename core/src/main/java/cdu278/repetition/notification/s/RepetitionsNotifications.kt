package cdu278.repetition.notification.s

import kotlinx.datetime.LocalDateTime

interface RepetitionsNotifications {

    suspend fun schedule(repetitionId: Long, date: LocalDateTime)

    fun cancel(repetitionId: Long)

    suspend fun remove()
}