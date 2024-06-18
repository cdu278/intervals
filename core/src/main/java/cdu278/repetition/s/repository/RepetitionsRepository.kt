package cdu278.repetition.s.repository

import kotlinx.coroutines.flow.Flow
import cdu278.repetition.Repetition
import cdu278.repetition.item.RepetitionItem
import cdu278.repetition.repository.RepetitionRepository

interface RepetitionsRepository {

    val itemsFlow: Flow<List<RepetitionItem>>

    suspend fun create(repetition: Repetition): Long

    suspend fun findById(id: Long): Repetition

    fun flowById(id: Long): Flow<Repetition>

    fun repetitionRepository(id: Long): RepetitionRepository
}