package cdu278.repetition.notification.repository

import cdu278.repetition.notification.db.dao.RepetitionNotificationDao
import cdu278.repetition.Repetition
import cdu278.repetition.notification.repository.RepetitionNotificationRepository

class RoomRepetitionNotificationRepository(
    private val dao: RepetitionNotificationDao
) : RepetitionNotificationRepository {

    override suspend fun findById(id: Long): Repetition {
        return dao.selectById(id)
    }
}