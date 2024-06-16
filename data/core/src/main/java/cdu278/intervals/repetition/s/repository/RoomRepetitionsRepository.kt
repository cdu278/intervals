package cdu278.intervals.repetition.s.repository

import cdu278.intervals.repetition.db.dao.RepetitionDao
import cdu278.intervals.repetition.db.entity.RepetitionEntity.Companion.asEntity
import cdu278.intervals.repetition.repository.RoomRepetitionRepository
import cdu278.intervals.repetition.s.db.dao.RepetitionsDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import midget17468.repetition.Repetition
import midget17468.repetition.item.RepetitionItem
import midget17468.repetition.item.RepetitionItem.Companion.asItem
import midget17468.repetition.repository.RepetitionRepository
import midget17468.repetition.s.repository.RepetitionsRepository

class RoomRepetitionsRepository(
    private val dao: RepetitionsDao,
    private val repetitionDao: RepetitionDao,
) : RepetitionsRepository {

    override val itemsFlow: Flow<List<RepetitionItem>>
        get() = dao.selectAll().map { repetitions ->
            repetitions.map { it.asItem() }
        }

    override suspend fun create(repetition: Repetition): Long {
        return dao.insert(repetition.asEntity())
    }

    override suspend fun findById(id: Long): Repetition {
        return dao.selectById(id)
    }

    override fun flowById(id: Long): Flow<Repetition> {
        return dao.selectFlowById(id)
    }

    override fun repetitionRepository(id: Long): RepetitionRepository {
        return RoomRepetitionRepository(
            repetitionId = id,
            repetitionDao,
        )
    }
}