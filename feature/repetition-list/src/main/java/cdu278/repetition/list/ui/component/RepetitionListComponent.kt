package cdu278.repetition.list.ui.component

import cdu278.decompose.context.coroutineScope
import cdu278.intervals.ui.component.context.IntervalsComponentContext
import cdu278.repetition.RepetitionState.Forgotten
import cdu278.repetition.RepetitionState.Repetition
import cdu278.repetition.RepetitionType
import cdu278.repetition.info.ui.UiRepetitionInfo
import cdu278.repetition.item.RepetitionItem
import cdu278.repetition.list.ui.RepetitionListState
import cdu278.repetition.list.ui.RepetitionListState.Default
import cdu278.repetition.list.ui.RepetitionListState.Selection
import cdu278.repetition.list.ui.RepetitionListUi
import cdu278.repetition.next.mapping.NextRepetitionDateMapping
import cdu278.repetition.s.repository.RepetitionsRepository
import cdu278.repetition.state.ui.UiRepetitionState
import cdu278.state.State
import cdu278.state.collectValues
import cdu278.ui.action.UiAction
import cdu278.updates.Updates
import com.arkivanov.essenty.backhandler.BackCallback
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.launch
import cdu278.repetition.list.ui.RepetitionListUi.State.NonEmpty.Mode.Default as DefaultMode
import cdu278.repetition.list.ui.RepetitionListUi.State.NonEmpty.Mode.Default.Item as DefaultItem
import cdu278.repetition.list.ui.RepetitionListUi.State.NonEmpty.Mode.Selection as SelectionMode
import cdu278.repetition.list.ui.RepetitionListUi.State.NonEmpty.Mode.Selection.Item as SelectionItem

class RepetitionListComponent(
    context: IntervalsComponentContext,
    private val repository: RepetitionsRepository,
    private val selectedTabTypeFlow: Flow<RepetitionType?>,
    private val state: State<RepetitionListState>,
    private val updates: Updates,
    private val goToRepetition: (repetitionId: Long) -> Unit,
    private val nextRepetitionDateMapping: NextRepetitionDateMapping = NextRepetitionDateMapping(),
) : IntervalsComponentContext by context {

    private val coroutineScope = coroutineScope()

    init {
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

    internal val uiModelFlow: StateFlow<RepetitionListUi> =
        state.handle(
            coroutineScope,
            initialValue = RepetitionListUi(null)
        ) { state ->
            channelFlow {
                var job: Job? = null
                updates.flow.collect {
                    job?.cancel()
                    job =
                        combine(
                            repository.itemsFlow,
                            selectedTabTypeFlow,
                        ) { allItems, selectedType ->
                            val notSortedItems =
                                selectedType
                                    ?.let { selected ->
                                        allItems.filter { it.type == selected }
                                    }
                                    ?: allItems
                            send(
                                RepetitionListUi(
                                    state = if (notSortedItems.isEmpty()) {
                                        RepetitionListUi.State.Empty
                                    } else {
                                        val items = notSortedItems.sorted()
                                        RepetitionListUi.State.NonEmpty(
                                            mode = when (state) {
                                                is Default ->
                                                    DefaultMode(
                                                        items = items.asDefaultUiItems(),
                                                    )

                                                is Selection ->
                                                    SelectionMode(
                                                        items = items.asSelectionUiItems(
                                                            state.idsOfSelected
                                                        ),
                                                    )
                                            },
                                        )
                                    }
                                )
                            )
                        }.flowOn(Dispatchers.Default).launchIn(this)
                }
            }
        }

    private fun List<RepetitionItem>.sorted(): List<RepetitionItem> {
        val sortByDate = (this.first().repetitionState as? Repetition)
            ?.let { it.date > currentTime() }
            ?: false
        return if (sortByDate) {
            this.sortedBy { (it.repetitionState as Repetition).date }
        } else {
            this.sortedBy(RepetitionItem::label)
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
                                repeat = UiAction(key = item.id) { goToRepetition(item.id) },
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
                            remember = UiAction(key = item.id) { goToRepetition(item.id) },
                        )
                },
                select = UiAction(key = item.id) { goToSelectionMode(item.id) },
                progressRatio = item.progressRatio,
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
                item.progressRatio,
            )
        }
    }

    private val RepetitionItem.progressRatio: Double?
        get() = (this.repetitionState as? Repetition)
            ?.let { repetitionIntervals.progressRatio(it.stage) }

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