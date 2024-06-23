package cdu278.repetition.s.repository

import cdu278.db.IntervalsDb
import cdu278.repetition.Repetition
import cdu278.repetition.db.entity.RepetitionEntity.Companion.asEntity
import cdu278.repetition.item.RepetitionItem
import cdu278.repetition.item.RepetitionItem.Companion.asItem
import cdu278.repetition.repository.RepetitionRepository
import cdu278.repetition.repository.RoomRepetitionRepository
import cdu278.repetition.s.db.dao.RepetitionsDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RoomRepetitionsRepository(
    private val dao: RepetitionsDao,
    private val repetitionRepositoryFactory: (id: Long) -> RepetitionRepository,
) : RepetitionsRepository {

    constructor(db: IntervalsDb) :
            this(
                db.repetitionsDao,
                repetitionRepositoryFactory = { id ->
                    RoomRepetitionRepository(
                        id,
                        db.repetitionDao,
                    )
                }
            )

    override val itemsFlow: Flow<List<RepetitionItem>>
        get() = dao.selectAll().map { repetitions ->
            repetitions.map { it.asItem() }
        }

    override suspend fun create(repetition: Repetition): Long {
        return dao.insert(repetition.asEntity())
    }

    override fun repetitionRepository(id: Long): RepetitionRepository {
        return repetitionRepositoryFactory.invoke(id)
    }

    override suspend fun findByLabel(label: String): Long? {
        return dao.selectByLabel(label)
    }
}