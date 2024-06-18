package cdu278.repetition.notification.repository

import cdu278.repetition.Repetition

interface RepetitionNotificationRepository {

    suspend fun findById(id: Long): Repetition
}