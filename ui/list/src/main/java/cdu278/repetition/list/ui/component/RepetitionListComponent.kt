package cdu278.repetition.list.ui.component

import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import cdu278.datetime.currentTime
import cdu278.decompose.context.coroutineScope
import cdu278.loadable.ui.Loadable
import cdu278.state.State
import cdu278.repetition.RepetitionState.Forgotten
import cdu278.repetition.RepetitionState.Repetition
import cdu278.repetition.item.ui.UiRepetitionItem
import cdu278.repetition.list.ui.RepetitionListInput
import cdu278.repetition.next.mapping.NextRepetitionDateMapping
import cdu278.repetition.notification.s.RepetitionsNotifications
import cdu278.repetition.s.repository.RepetitionsRepository
import cdu278.ui.action.UiAction

class RepetitionListComponent internal constructor(
    context: ComponentContext,
    private val repository: RepetitionsRepository,
    private val repetitionNotifications: RepetitionsNotifications,
    private val repeat: (repetitionId: Long) -> Unit,
    private val nextRepetitionDateMapping: NextRepetitionDateMapping,
    private val currentTime: () -> LocalDateTime,
) : ComponentContext by context {

    constructor(
        context: ComponentContext,
        repository: RepetitionsRepository,
        repetitionNotifications: RepetitionsNotifications,
        repeat: (repetitionId: Long) -> Unit,
    ) : this(
        context,
        repository,
        repetitionNotifications,
        repeat,
        nextRepetitionDateMapping = NextRepetitionDateMapping(),
        currentTime = { Clock.System.currentTime() },
    )

    private val state: State<RepetitionListInput> =
        State(
            stateKeeper.consume("input", RepetitionListInput.serializer())
                ?: RepetitionListInput()
        )

    init {
        stateKeeper.register("input", RepetitionListInput.serializer()) { state.value }
    }

    private val coroutineScope = coroutineScope()

    internal val uiModelFlow: StateFlow<Loadable<List<UiRepetitionItem>>> =
        state.handle(coroutineScope, initialValue = Loadable.Loading) { input ->
            repository
                .itemsFlow
                .map { items ->
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
            repetitionNotifications.remove(id)
        }
    }
}