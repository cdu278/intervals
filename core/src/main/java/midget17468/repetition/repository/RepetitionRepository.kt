package midget17468.repetition.repository

import kotlinx.coroutines.flow.Flow
import midget17468.repetition.Repetition
import midget17468.repetition.item.RepetitionItem
import midget17468.repetition.RepetitionState

interface RepetitionRepository {

    val itemsFlow: Flow<List<RepetitionItem>>

    suspend fun create(repetition: Repetition): Int

    suspend fun findById(id: Int): Repetition

    fun flowById(id: Int): Flow<Repetition>

    suspend fun update(id: Int, updatedState: (Repetition) -> RepetitionState)

    suspend fun delete(id: Int, onCommit: () -> Unit = { })
}