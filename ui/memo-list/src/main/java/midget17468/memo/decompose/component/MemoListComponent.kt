package midget17468.memo.decompose.component

import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import midget17468.compose.context.coroutineScope
import midget17468.datetime.currentTime
import midget17468.input.Input
import midget17468.memo.mapping.ui.NextRepetitionDateMapping
import midget17468.memo.model.domain.RepetitionState.Forgotten
import midget17468.memo.model.domain.RepetitionState.Repetition
import midget17468.memo.model.input.MemoListInput
import midget17468.memo.model.ui.UiMemoItem
import midget17468.memo.repetitions.notifications.RepetitionsNotifications
import midget17468.memo.repository.MemoRepository
import midget17468.model.ui.Loadable
import midget17468.model.ui.UiAction

class MemoListComponent internal constructor(
    context: ComponentContext,
    private val repository: MemoRepository,
    private val repetitionNotifications: RepetitionsNotifications,
    private val repeat: (memoId: Int) -> Unit,
    private val nextRepetitionDateMapping: NextRepetitionDateMapping,
    private val currentTime: () -> LocalDateTime,
) : ComponentContext by context {

    constructor(
        context: ComponentContext,
        repository: MemoRepository,
        repetitionNotifications: RepetitionsNotifications,
        repeat: (memoId: Int) -> Unit,
    ) : this(
        context,
        repository,
        repetitionNotifications,
        repeat,
        nextRepetitionDateMapping = NextRepetitionDateMapping(),
        currentTime = { Clock.System.currentTime() },
    )

    private val input: Input<MemoListInput> =
        Input(
            stateKeeper.consume("input", MemoListInput.serializer())
                ?: MemoListInput()
        )

    init {
        stateKeeper.register("input", MemoListInput.serializer()) { input.value }
    }

    private val coroutineScope = coroutineScope()

    internal val uiModelFlow: StateFlow<Loadable<List<UiMemoItem>>> =
        input.handle(coroutineScope, initialValue = Loadable.Loading) { input ->
            repository
                .itemsFlow
                .map { items ->
                    Loadable.Loaded(
                        items.map { item ->
                            UiMemoItem(
                                item.id,
                                UiMemoItem.Info(item.label, item.type),
                                state = when (val state = item.repetitionState) {
                                    is Repetition ->
                                        if (currentTime() > state.date) {
                                            UiMemoItem.State.Repetition
                                        } else {
                                            UiMemoItem.State.RepetitionAt(
                                                date = with(nextRepetitionDateMapping) {
                                                    state.date.toUiModel()
                                                }
                                            )
                                        }

                                    is Forgotten -> UiMemoItem.State.Forgotten
                                },
                                repeat = UiAction { repeat(item.id) },
                                UiMemoItem.Expanded(
                                    input.idOfExpanded == item.id,
                                    toggle = UiAction { toggleExpanded(item.id) }
                                ),
                                delete = UiAction { delete(item.id) },
                            )
                        }
                    )
                }
        }

    private fun toggleExpanded(id: Int) {
        input.update {
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