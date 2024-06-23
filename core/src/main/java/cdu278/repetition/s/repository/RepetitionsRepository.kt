package cdu278.repetition.s.repository

import cdu278.repetition.Repetition
import cdu278.repetition.item.RepetitionItem
import cdu278.repetition.repository.RepetitionRepository
import kotlinx.coroutines.flow.Flow

interface RepetitionsRepository {

    val itemsFlow: Flow<List<RepetitionItem>>

    suspend fun create(repetition: Repetition): Long

    fun repetitionRepository(id: Long): RepetitionRepository

    suspend fun findByLabel(label: String): Long?
}