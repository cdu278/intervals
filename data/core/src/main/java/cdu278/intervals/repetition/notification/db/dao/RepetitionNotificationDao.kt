package cdu278.intervals.repetition.notification.db.dao

import androidx.room.Dao
import androidx.room.Query
import cdu278.intervals.repetition.db.entity.RepetitionEntity

@Dao
interface RepetitionNotificationDao {

    @Query("SELECT * FROM repetition WHERE id = :id")
    suspend fun selectById(id: Long): RepetitionEntity
}