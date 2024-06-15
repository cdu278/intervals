package midget17468.repetition.list.ui.component

import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import midget17468.datetime.currentTime
import midget17468.decompose.context.coroutineScope
import midget17468.loadable.ui.Loadable
import midget17468.state.State
import midget17468.repetition.RepetitionState.Forgotten
import midget17468.repetition.RepetitionState.Repetition
import midget17468.repetition.item.ui.UiRepetitionItem
import midget17468.repetition.list.ui.RepetitionListInput
import midget17468.repetition.next.mapping.NextRepetitionDateMapping
import midget17468.repetition.notification.s.RepetitionsNotifications
import midget17468.repetition.repository.RepetitionRepository
import midget17468.ui.action.UiAction

class RepetitionListComponent internal constructor(
    context: ComponentContext,
    private val repository: RepetitionRepository,
    private val repetitionNotifications: RepetitionsNotifications,
    private val repeat: (repetitionId: Int) -> Unit,
    private val nextRepetitionDateMapping: NextRepetitionDateMapping,
    private val currentTime: () -> LocalDateTime,
) : ComponentContext by context {

    constructor(
        context: ComponentContext,
        repository: RepetitionRepository,
        repetitionNotifications: RepetitionsNotifications,
        repeat: (repetitionId: Int) -> Unit,
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

    private fun toggleExpanded(id: Int) {
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

    private fun delete(id: Int) {
        coroutineScope.launch {
            repository.delete(id, onCommit = {
                launch { repetitionNotifications.remove(id) }
            })
        }
    }
}