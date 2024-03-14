package midget17468.memo.decompose.component

import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import midget17468.compose.context.coroutineScope
import midget17468.datetime.currentTime
import midget17468.memo.mapping.ui.NextRepetitionDateMapping
import midget17468.memo.model.domain.RepetitionState.Forgotten
import midget17468.memo.model.domain.RepetitionState.Repetition
import midget17468.memo.model.ui.UiMemoItem
import midget17468.memo.repository.MemoRepository
import midget17468.model.ui.Loadable
import midget17468.model.ui.UiAction

class MemoListComponent internal constructor(
    context: ComponentContext,
    repository: MemoRepository,
    private val repeat: (memoId: Int) -> Unit,
    private val nextRepetitionDateMapping: NextRepetitionDateMapping,
    private val currentTime: () -> LocalDateTime,
) : ComponentContext by context {

    constructor(
        context: ComponentContext,
        repository: MemoRepository,
        repeat: (memoId: Int) -> Unit,
    ) : this(
        context,
        repository,
        repeat,
        nextRepetitionDateMapping = NextRepetitionDateMapping(),
        currentTime = { Clock.System.currentTime() },
    )

    internal val uiModelFlow: StateFlow<Loadable<List<UiMemoItem>>> =
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
                        )
                    }
                )
            }
            .stateIn(coroutineScope(), SharingStarted.Eagerly, initialValue = Loadable.Loading)
}