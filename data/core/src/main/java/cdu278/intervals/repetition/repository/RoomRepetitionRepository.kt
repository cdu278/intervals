package cdu278.intervals.repetition.repository

import cdu278.intervals.repetition.db.dao.RepetitionDao
import midget17468.repetition.Repetition
import midget17468.repetition.RepetitionState
import midget17468.repetition.repository.RepetitionRepository

class RoomRepetitionRepository(
    private val repetitionId: Long,
    private val dao: RepetitionDao,
) : RepetitionRepository {

    override suspend fun updateState(updatedState: (Repetition) -> RepetitionState) {
        dao.updateState(repetitionId, updatedState)
    }

    override suspend fun delete() {
        dao.delete(repetitionId)
    }
}