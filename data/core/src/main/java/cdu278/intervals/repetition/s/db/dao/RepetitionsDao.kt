package cdu278.intervals.repetition.s.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import cdu278.intervals.repetition.db.entity.RepetitionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RepetitionsDao {

    @Query("SELECT * FROM repetition")
    fun selectAll(): Flow<List<RepetitionEntity>>

    @Insert
    suspend fun insert(repetition: RepetitionEntity): Long

    @Query("SELECT * FROM repetition WHERE id = :id")
    suspend fun selectById(id: Long): RepetitionEntity

    @Query("SELECT * FROM repetition WHERE id = :id")
    fun selectFlowById(id: Long): Flow<RepetitionEntity>
}