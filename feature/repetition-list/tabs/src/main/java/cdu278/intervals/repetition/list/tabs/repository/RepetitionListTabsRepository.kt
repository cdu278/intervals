package cdu278.intervals.repetition.list.tabs.repository

import cdu278.repetition.RepetitionType
import cdu278.repetition.s.repository.RepetitionsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

class RepetitionListTabsRepository(
    private val repetitionsRepository: RepetitionsRepository,
) {

    val presentRepetitionTypesFlow: Flow<List<RepetitionType>>
        get() = repetitionsRepository.itemsFlow.map { items ->
            items
                .map { it.type }
                .toSet()
                .toList()
        }.flowOn(Dispatchers.Default)
}