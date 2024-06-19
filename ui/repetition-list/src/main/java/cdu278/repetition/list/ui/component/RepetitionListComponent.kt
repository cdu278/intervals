package cdu278.repetition.list.ui.component

import cdu278.datetime.currentTime
import cdu278.decompose.context.coroutineScope
import cdu278.intervals.ui.component.context.IntervalsComponentContext
import cdu278.loadable.ui.Loadable
import cdu278.repetition.RepetitionState.Forgotten
import cdu278.repetition.RepetitionState.Repetition
import cdu278.repetition.item.ui.UiRepetitionItem
import cdu278.repetition.list.ui.RepetitionListInput
import cdu278.repetition.next.mapping.NextRepetitionDateMapping
import cdu278.repetition.s.repository.RepetitionsRepository
import cdu278.state.State
import cdu278.ui.action.UiAction
import cdu278.updates.Updates
import com.arkivanov.essenty.lifecycle.doOnResume
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime

class RepetitionListComponent internal constructor(
    context: IntervalsComponentContext,
    private val repository: RepetitionsRepository,
    private val repeat: (repetitionId: Long) -> Unit,
    private val nextRepetitionDateMapping: NextRepetitionDateMapping,
    private val currentTime: () -> LocalDateTime,
) : IntervalsComponentContext by context {

    constructor(
        context: IntervalsComponentContext,
        repository: RepetitionsRepository,
        repeat: (repetitionId: Long) -> Unit,
    ) : this(
        context,
        repository,
        repeat,
        nextRepetitionDateMapping = NextRepetitionDateMapping(),
        currentTime = { Clock.System.currentTime() },
    )

    private val state: State<RepetitionListInput> =
        State(
            stateKeeper.consume("input", RepetitionListInput.serializer())
                ?: RepetitionListInput()
        )

    private val updates = Updates()

    init {
        stateKeeper.register("input", RepetitionListInput.serializer()) { state.value }

        lifecycle.doOnResume {
            updates.post()
        }
    }

    private val coroutineScope = coroutineScope()

    internal val uiModelFlow: StateFlow<Loadable<List<UiRepetitionItem>>> =
        state.handle(coroutineScope, initialValue = Loadable.Loading) { input ->
            combine(
                repository.itemsFlow,
                updates.flow
            ) { items, _ ->
                Loadable.Loaded(
                    items.map { item ->
                        UiRepetitionItem(
                            item.id,
                            UiRepetitionItem.Info(item.label, item.type),
                            state = when (val state = item.repetitionState) {
                                is Repetition ->
                                    if (currentTime() > state.date) {
                                        UiRepetitionItem.State.Repetition
                                    } else {
                                        UiRepetitionItem.State.RepetitionAt(
                                            date = with(nextRepetitionDateMapping) {
                                                state.date.toUiModel()
                                            }
                                        )
                                    }

                                is Forgotten -> UiRepetitionItem.State.Forgotten
                            },
                            repeat = UiAction(key = item.id) { repeat(item.id) },
                            UiRepetitionItem.Expanded(
                                input.idOfExpanded == item.id,
                                toggle = UiAction(key = item.id) { toggleExpanded(item.id) }
                            ),
                            delete = UiAction(key = item.id) { delete(item.id) },
                        )
                    }
                )
            }
        }

    private fun toggleExpanded(id: Long) {
        state.update {
            it.copy(
                idOfExpanded = if (id == it.idOfExpanded) {
                    null
                } else {
                    id
                }
            )
        }
    }

    private fun delete(id: Long) {
        coroutineScope.launch {
            repository
                .repetitionRepository(id)
                .delete()
        }
    }
}