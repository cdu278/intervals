package cdu278.repetition.notification.s

import kotlinx.datetime.LocalDateTime

interface RepetitionsNotifications {

    suspend fun schedule(repetitionId: Long, date: LocalDateTime)

    suspend fun remove(repetitionId: Long)
}