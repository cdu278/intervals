package cdu278.repetition.list.tabs.repository

import cdu278.repetition.RepetitionType
import cdu278.repetition.s.repository.RepetitionsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

class DefaultRepetitionListTabsRepository(
    private val repetitionsRepository: RepetitionsRepository,
) : RepetitionListTabsRepository {

    override val presentRepetitionTypesFlow: Flow<List<RepetitionType>>
        get() = repetitionsRepository.itemsFlow.map { items ->
            items
                .map { it.type }
                .toSet()
                .toList()
                .sortedBy { RepetitionType.entries.indexOf(it) }
        }.flowOn(Dispatchers.Default)
}