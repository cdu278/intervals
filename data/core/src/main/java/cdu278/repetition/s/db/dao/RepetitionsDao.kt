package cdu278.repetition.s.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import cdu278.repetition.db.entity.RepetitionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RepetitionsDao {

    @Query("SELECT * FROM repetition")
    fun selectAll(): Flow<List<RepetitionEntity>>

    @Insert
    suspend fun insert(repetition: RepetitionEntity): Long
}