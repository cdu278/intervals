package midget17468.repetition.notification.repository

import midget17468.repetition.Repetition

interface RepetitionNotificationRepository {

    suspend fun findById(id: Long): Repetition
}