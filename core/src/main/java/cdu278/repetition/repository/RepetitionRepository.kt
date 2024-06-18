package cdu278.repetition.repository

import cdu278.repetition.Repetition
import cdu278.repetition.RepetitionState

interface RepetitionRepository {

    suspend fun updateState(updatedState: (Repetition) -> RepetitionState)

    suspend fun delete()
}