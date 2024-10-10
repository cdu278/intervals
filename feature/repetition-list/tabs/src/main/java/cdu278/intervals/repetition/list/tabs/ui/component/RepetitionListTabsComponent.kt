package cdu278.intervals.repetition.list.tabs.ui.component

import cdu278.decompose.context.coroutineScope
import cdu278.intervals.repetition.list.tabs.repository.RepetitionListTabsRepository
import cdu278.intervals.repetition.list.tabs.ui.RepetitionTabUi
import cdu278.intervals.ui.component.context.IntervalsComponentContext
import cdu278.repetition.RepetitionType
import cdu278.state.State
import cdu278.ui.action.UiAction
import cdu278.ui.input.UiToggleable
import cdu278.updates.Updates
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class RepetitionListTabsComponent(
    context: IntervalsComponentContext,
    private val updates: Updates,
    private val repository: RepetitionListTabsRepository,
) : IntervalsComponentContext by context {

    private val selectedTabTypeState: State<RepetitionType?> =
        State(stateKeeper.consume("selectedTab", RepetitionType.serializer()))

    private val coroutineScope = coroutineScope()

    val selectedTabTypeFlow: StateFlow<RepetitionType?> =
        selectedTabTypeState.handle(coroutineScope, RepetitionType.Password) { selectedType ->
            flowOf(selectedType)
        }

    init {
        combine(
            repository.presentRepetitionTypesFlow,
            selectedTabTypeFlow,
        ) { presentTypes, selectedType ->
            if (selectedType !in presentTypes) {
                selectedTabTypeState.update { null }
            }
        }.launchIn(coroutineScope)
    }

    internal val tabsFlow: StateFlow<List<RepetitionTabUi>?> =
        selectedTabTypeState.handle(coroutineScope, initialValue = emptyList()) { selectedType ->
            channelFlow {
                var updating: Job? = null
                updates.flow.collect {
                    updating?.cancel()
                    updating = repository.presentRepetitionTypesFlow.onEach { presentTypes ->
                        val typesWithNull = listOf(null) + presentTypes
                        typesWithNull
                            .takeIf { it.size > 2 }
                            ?.map { type ->
                                val selected = type == selectedType
                                RepetitionTabUi(
                                    type,
                                    selected = UiToggleable(
                                        selected,
                                        toggle = UiAction(key = type) {
                                            selectedTabTypeState.update { type }
                                        }
                                    )
                                )
                            }
                            .let { send(it) }
                    }.launchIn(this)
                }
            }
        }
}