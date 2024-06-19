package cdu278.repetition.notification.db.dao

import androidx.room.Dao
import androidx.room.Query
import cdu278.repetition.notification.NotificationRepetition

@Dao
interface RepetitionNotificationDao {

    @Query("SELECT label, state FROM repetition")
    suspend fun selectAll(): List<NotificationRepetition>
}