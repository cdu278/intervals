package cdu278.repetition.repository

import cdu278.repetition.db.dao.RepetitionDao
import cdu278.repetition.Repetition
import cdu278.repetition.RepetitionState
import cdu278.repetition.repository.RepetitionRepository

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