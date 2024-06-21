package cdu278.repetition.list.ui.component

import cdu278.datetime.currentTime
import cdu278.decompose.context.coroutineScope
import cdu278.intervals.ui.component.context.IntervalsComponentContext
import cdu278.repetition.RepetitionState.Forgotten
import cdu278.repetition.RepetitionState.Repetition
import cdu278.repetition.info.ui.UiRepetitionInfo
import cdu278.repetition.item.RepetitionItem
import cdu278.repetition.list.ui.RepetitionListState
import cdu278.repetition.list.ui.RepetitionListState.Default
import cdu278.repetition.list.ui.RepetitionListState.Selection
import cdu278.repetition.list.ui.UiRepetitionList
import cdu278.repetition.next.mapping.NextRepetitionDateMapping
import cdu278.repetition.s.repository.RepetitionsRepository
import cdu278.repetition.state.ui.UiRepetitionState
import cdu278.state.State
import cdu278.state.collectValues
import cdu278.ui.action.UiAction
import cdu278.updates.Updates
import com.arkivanov.essenty.backhandler.BackCallback
import com.arkivanov.essenty.lifecycle.doOnResume
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import cdu278.repetition.list.ui.UiRepetitionList.NonEmpty.Mode.Default as DefaultMode
import cdu278.repetition.list.ui.UiRepetitionList.NonEmpty.Mode.Default.Item as DefaultItem
import cdu278.repetition.list.ui.UiRepetitionList.NonEmpty.Mode.Selection as SelectionMode
import cdu278.repetition.list.ui.UiRepetitionList.NonEmpty.Mode.Selection.Item as SelectionItem

class RepetitionListComponent internal constructor(
    context: IntervalsComponentContext,
    private val repository: RepetitionsRepository,
    private val state: State<RepetitionListState>,
    private val repeat: (repetitionId: Long) -> Unit,
    private val nextRepetitionDateMapping: NextRepetitionDateMapping,
    private val currentTime: () -> LocalDateTime,
) : IntervalsComponentContext by context {

    constructor(
        context: IntervalsComponentContext,
        repository: RepetitionsRepository,
        state: State<RepetitionListState>,
        repeat: (repetitionId: Long) -> Unit,
    ) : this(
        context,
        repository,
        state,
        repeat,
        nextRepetitionDateMapping = NextRepetitionDateMapping(),
        currentTime = { Clock.System.currentTime() },
    )

    private val updates = Updates()

    private val coroutineScope = coroutineScope()

    init {
        stateKeeper.register("state", RepetitionListState.serializer()) { state.value }

        lifecycle.doOnResume {
            updates.post()
        }

        coroutineScope.launch(Dispatchers.Main) {
            var backCallback: BackCallback? = null
            state.collectValues { state ->
                when (state) {
                    is Default ->
                        backCallback?.let {
                            backHandler.unregister(it)
                            backCallback = null
                        }
                    is Selection ->
                        if (backCallback == null) {
                            SelectionModeBackCallback(this@RepetitionListComponent.state)
                                .also { backHandler.register(it) }
                                .also { backCallback = it }
                        }
                }
            }
        }
    }

    internal val uiModelFlow: StateFlow<UiRepetitionList?> =
        state.handle(coroutineScope, initialValue = null) { state ->
            combine(
                repository.itemsFlow,
                updates.flow
            ) { items, _ ->
                if (items.isEmpty()) return@combine UiRepetitionList.Empty
                UiRepetitionList.NonEmpty(
                    mode = when (state) {
                        is Default ->
                            DefaultMode(
                                items = items.asDefaultUiItems(),
                            )
                        is Selection ->
                            SelectionMode(
                                items = items.asSelectionUiItems(state.idsOfSelected),
                            )
                    }
                )
            }
        }

    private fun List<RepetitionItem>.asDefaultUiItems(): List<DefaultItem> {
        return map { item ->
            DefaultItem(
                UiRepetitionInfo(item),
                state = when (val state = item.repetitionState) {
                    is Repetition ->
                        if (currentTime() > state.date) {
                            UiRepetitionState.Repetition(
                                repeat = UiAction(key = item.id) { repeat(item.id) },
                            )
                        } else {
                            UiRepetitionState.RepetitionAt(
                                date = with(nextRepetitionDateMapping) {
                                    state.date.toUiModel()
                                }
                            )
                        }
                    is Forgotten ->
                        UiRepetitionState.Forgotten(
                            remember = UiAction(key = item.id) { repeat(item.id) },
                        )
                },
                select = UiAction(key = item.id) { goToSelectionMode(item.id) },
            )
        }
    }

    private fun List<RepetitionItem>.asSelectionUiItems(
        idsOfSelected: List<Long>
    ): List<SelectionItem> {
        return map { item ->
            SelectionItem(
                UiRepetitionInfo(item),
                selected = item.id in idsOfSelected,
                toggleSelected = UiAction(key = item.id) { toggleSelected(item.id) },
            )
        }
    }

    private fun goToSelectionMode(repetitionId: Long) {
        state.update { Selection(idsOfSelected = listOf(repetitionId)) }
    }

    private fun toggleSelected(repetitionId: Long) {
        state.update { current ->
            (current as Selection).copy(
                idsOfSelected = if (repetitionId in current.idsOfSelected) {
                    current
                        .idsOfSelected.filter { it != repetitionId }
                        .takeIf { it.isNotEmpty() }
                        ?: return@update Default
                } else {
                    current.idsOfSelected + repetitionId
                }
            )
        }
    }
}