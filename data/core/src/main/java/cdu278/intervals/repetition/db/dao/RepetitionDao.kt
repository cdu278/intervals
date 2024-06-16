package cdu278.intervals.repetition.db.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import cdu278.intervals.repetition.db.entity.RepetitionEntity
import midget17468.repetition.Repetition
import midget17468.repetition.RepetitionState

@Dao
abstract class RepetitionDao {

    @Query("DELETE FROM repetition WHERE id = :id")
    abstract suspend fun delete(id: Long)

    @Transaction
    open suspend fun updateState(id: Long, updatedState: (Repetition) -> RepetitionState) {
        val repetition = selectById(id)
        update(repetition.copy(state = updatedState(repetition)))
    }

    @Query("SELECT * FROM repetition WHERE id = :id")
    protected abstract suspend fun selectById(id: Long): RepetitionEntity

    @Update
    protected abstract suspend fun update(repetition: RepetitionEntity)
}