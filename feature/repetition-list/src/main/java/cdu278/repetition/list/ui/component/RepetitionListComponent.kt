package cdu278.repetition.list.ui.component

import cdu278.decompose.context.coroutineScope
import cdu278.intervals.repetition.list.tabs.repository.RepetitionListTabsRepository
import cdu278.intervals.repetition.list.tabs.ui.component.RepetitionListTabsComponent
import cdu278.intervals.ui.component.context.IntervalsComponentContext
import cdu278.intervals.ui.component.context.childContext
import cdu278.repetition.RepetitionState.Forgotten
import cdu278.repetition.RepetitionState.Repetition
import cdu278.repetition.info.ui.UiRepetitionInfo
import cdu278.repetition.item.RepetitionItem
import cdu278.repetition.list.ui.RepetitionListState
import cdu278.repetition.list.ui.RepetitionListState.Mode.Default
import cdu278.repetition.list.ui.RepetitionListState.Mode.Selection
import cdu278.repetition.list.ui.RepetitionListUi
import cdu278.repetition.next.mapping.NextRepetitionDateMapping
import cdu278.repetition.s.repository.RepetitionsRepository
import cdu278.repetition.state.ui.UiRepetitionState
import cdu278.state.State
import cdu278.state.collectValues
import cdu278.state.prop
import cdu278.ui.action.UiAction
import cdu278.updates.Updates
import com.arkivanov.essenty.backhandler.BackCallback
import com.arkivanov.essenty.lifecycle.doOnResume
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
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
    private val state: State<RepetitionListState>,
    private val repository: RepetitionsRepository,
    private val goToRepetition: (repetitionId: Long) -> Unit,
    private val nextRepetitionDateMapping: NextRepetitionDateMapping = NextRepetitionDateMapping(),
) : IntervalsComponentContext by context {

    private val updates = Updates()

    private val coroutineScope = coroutineScope()

    init {
        lifecycle.doOnResume {
            updates.post()
        }

        coroutineScope.launch(Dispatchers.Main) {
            var backCallback: BackCallback? = null
            state.collectValues { state ->
                when (state.mode) {
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

    private val tabsComponent =
        RepetitionListTabsComponent(
            childContext("tabs"),
            selectedTypeState = state.prop(RepetitionListState::selectedType) {
                copy(selectedType = it)
            },
            updates,
            RepetitionListTabsRepository(repository),
        )

    internal val uiModelFlow: StateFlow<RepetitionListUi> =
        state.handle(
            coroutineScope,
            initialValue = RepetitionListUi(tabsComponent, null)
        ) { state ->
            channelFlow {
                var job: Job? = null
                updates.flow.collect {
                    job?.cancel()
                    job =
                        combine(
                            repository.itemsFlow,
                            tabsComponent.selectedTabTypeFlow,
                        ) { allItems, selectedType ->
                            val notSortedItems =
                                selectedType
                                    ?.let { selected ->
                                        allItems.filter { it.type == selected }
                                    }
                                    ?: allItems
                            send(
                                RepetitionListUi(
                                    tabsComponent,
                                    state = if (notSortedItems.isEmpty()) {
                                        RepetitionListUi.State.Empty
                                    } else {
                                        val items = notSortedItems.sorted()
                                        RepetitionListUi.State.NonEmpty(
                                            mode = when (val mode = state.mode) {
                                                is Default ->
                                                    DefaultMode(
                                                        items = items.asDefaultUiItems(),
                                                    )
                                                is Selection ->
                                                    SelectionMode(
                                                        items = items
                                                            .asSelectionUiItems(mode.idsOfSelected),
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
        state.update { it.copy(mode = Selection(idsOfSelected = listOf(repetitionId))) }
    }

    private fun toggleSelected(repetitionId: Long) {
        state.update { current ->
            current.copy(
                mode = current.mode
                    .let { it as Selection }
                    .let { selection ->
                        selection.copy(
                            idsOfSelected = if (repetitionId in selection.idsOfSelected) {
                                selection
                                    .idsOfSelected.filter { it != repetitionId }
                                    .takeIf { it.isNotEmpty() }
                                    ?: return@let Default
                            } else {
                                selection.idsOfSelected + repetitionId
                            }
                        )
                    }
            )
        }
    }
}