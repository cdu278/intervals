package midget17468.repetition.s.repository

import kotlinx.coroutines.flow.Flow
import midget17468.repetition.Repetition
import midget17468.repetition.item.RepetitionItem
import midget17468.repetition.repository.RepetitionRepository

interface RepetitionsRepository {

    val itemsFlow: Flow<List<RepetitionItem>>

    suspend fun create(repetition: Repetition): Long

    suspend fun findById(id: Long): Repetition

    fun flowById(id: Long): Flow<Repetition>

    fun repetitionRepository(id: Long): RepetitionRepository
}