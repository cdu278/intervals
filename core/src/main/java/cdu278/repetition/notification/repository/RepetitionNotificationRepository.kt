package cdu278.repetition.notification.repository

import cdu278.repetition.notification.NotificationRepetition

interface RepetitionNotificationRepository {

    suspend fun findAll(): List<NotificationRepetition>
}