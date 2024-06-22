package cdu278.repetition.s.repository

import cdu278.predicate.Predicate
import cdu278.repetition.item.RepetitionItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class FilteringRepetitionsItemsRepository(
    private val original: RepetitionsRepository,
    private val predicate: Predicate<RepetitionItem>,
) : RepetitionsRepository by original {

    override val itemsFlow: Flow<List<RepetitionItem>>
        get() = original.itemsFlow.map { items ->
            items.filter { predicate.test(it) }
        }
}