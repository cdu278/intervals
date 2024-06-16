package cdu278.intervals.repetition.notification.repository

import cdu278.intervals.repetition.notification.db.dao.RepetitionNotificationDao
import midget17468.repetition.Repetition
import midget17468.repetition.notification.repository.RepetitionNotificationRepository

class RoomRepetitionNotificationRepository(
    private val dao: RepetitionNotificationDao
) : RepetitionNotificationRepository {

    override suspend fun findById(id: Long): Repetition {
        return dao.selectById(id)
    }
}