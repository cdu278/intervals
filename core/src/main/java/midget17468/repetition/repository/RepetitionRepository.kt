package midget17468.repetition.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import midget17468.repetition.Repetition
import midget17468.repetition.RepetitionQueries
import midget17468.repetition.RepetitionState
import midget17468.updates.Updates

class RepetitionRepository(
    private val id: Int,
    private val queries: RepetitionQueries,
    private val updates: Updates,
) {

    suspend fun updateState(updatedState: (Repetition) -> RepetitionState) {
        withContext(Dispatchers.IO) {
            queries.transaction {
                val repetition = queries.selectById(id).executeAsOne()
                queries.update(id = id, repetitionState = updatedState(repetition))
            }
        }
        updates.post()
    }

    suspend fun delete() {
        withContext(Dispatchers.IO) {
            queries.transaction {
                queries.delete(id)
            }
        }
        updates.post()
    }
}