package cdu278.repetition.repository

import cdu278.repetition.Repetition
import cdu278.repetition.RepetitionState
import kotlinx.coroutines.flow.Flow

interface RepetitionRepository {

    suspend fun get(): Repetition

    val flow: Flow<Repetition>

    suspend fun updateState(updatedState: (Repetition) -> RepetitionState)

    suspend fun delete()
}