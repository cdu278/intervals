package cdu278.repetition.db.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import cdu278.repetition.db.entity.RepetitionEntity
import cdu278.repetition.Repetition
import cdu278.repetition.RepetitionState
import kotlinx.coroutines.flow.Flow

@Dao
abstract class RepetitionDao {

    @Query("SELECT * FROM repetition WHERE id = :id")
    abstract suspend fun selectById(id: Long): RepetitionEntity

    @Query("SELECT * FROM repetition WHERE id = :id")
    abstract fun selectFlowById(id: Long): Flow<RepetitionEntity>

    @Query("DELETE FROM repetition WHERE id = :id")
    abstract suspend fun delete(id: Long)

    @Transaction
    open suspend fun updateState(id: Long, updatedState: (Repetition) -> RepetitionState) {
        val repetition = selectById(id)
        update(repetition.copy(state = updatedState(repetition)))
    }

    @Update
    protected abstract suspend fun update(repetition: RepetitionEntity)
}