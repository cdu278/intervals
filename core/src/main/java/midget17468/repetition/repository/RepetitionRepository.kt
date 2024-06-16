package midget17468.repetition.repository

import midget17468.repetition.Repetition
import midget17468.repetition.RepetitionState

interface RepetitionRepository {

    suspend fun updateState(updatedState: (Repetition) -> RepetitionState)

    suspend fun delete()
}