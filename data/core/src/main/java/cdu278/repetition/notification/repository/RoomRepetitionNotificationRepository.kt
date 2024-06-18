package cdu278.repetition.notification.repository

import cdu278.repetition.notification.NotificationRepetition
import cdu278.repetition.notification.db.dao.RepetitionNotificationDao

class RoomRepetitionNotificationRepository(
    private val dao: RepetitionNotificationDao,
) : RepetitionNotificationRepository {

    override suspend fun findAll(): List<NotificationRepetition> {
        return dao.selectAll()
    }
}