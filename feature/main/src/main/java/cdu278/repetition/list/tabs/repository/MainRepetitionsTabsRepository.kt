package cdu278.repetition.list.tabs.repository

import cdu278.predicate.Predicate
import cdu278.repetition.RepetitionType
import cdu278.repetition.item.RepetitionItem
import cdu278.repetition.s.repository.FilteringRepetitionsItemsRepository
import cdu278.repetition.s.repository.RepetitionsRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class MainRepetitionsTabsRepository(
    private val repetitionsRepository: RepetitionsRepository,
    private val filterFlow: Flow<Predicate<RepetitionItem>>,
) : RepetitionListTabsRepository {

    override val presentRepetitionTypesFlow: Flow<List<RepetitionType>>
        get() = channelFlow {
            var job: Job? = null
            filterFlow.collect { filter ->
                job?.cancel()
                job = DefaultRepetitionListTabsRepository(
                    FilteringRepetitionsItemsRepository(
                        original = repetitionsRepository,
                        predicate = filter
                    )
                ).presentRepetitionTypesFlow.onEach {
                    send(it)
                }.launchIn(this)
            }
        }
}