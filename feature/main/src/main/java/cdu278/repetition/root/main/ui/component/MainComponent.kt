package cdu278.repetition.root.main.ui.component

import cdu278.decompose.context.coroutineScope
import cdu278.decompose.util.asStateFlow
import cdu278.intervals.ui.component.context.IntervalsComponentContext
import cdu278.intervals.ui.component.context.childContext
import cdu278.intervals.ui.component.context.newContext
import cdu278.repetition.deletion.dialog.ui.component.RepetitionsDeletionDialogComponent
import cdu278.repetition.list.ui.RepetitionListState
import cdu278.repetition.list.ui.RepetitionListState.Default
import cdu278.repetition.list.ui.RepetitionListState.Selection
import cdu278.repetition.list.ui.component.RepetitionListComponent
import cdu278.repetition.new.flow.ui.component.NewRepetitionFlowComponent
import cdu278.repetition.root.main.tab.filter.ActiveRepetitionsFilter
import cdu278.repetition.root.main.tab.filter.ActualRepetitionsFilter
import cdu278.repetition.root.main.tab.filter.ArchivedRepetitionsFilter
import cdu278.repetition.root.main.ui.MainDialogConfig
import cdu278.repetition.root.main.ui.MainDialogConfig.Deletion
import cdu278.repetition.root.main.ui.MainTabConfig
import cdu278.repetition.root.main.ui.MainTabConfig.Active
import cdu278.repetition.root.main.ui.MainTabConfig.Actual
import cdu278.repetition.root.main.ui.MainTabConfig.Archive
import cdu278.repetition.root.main.ui.UiMain
import cdu278.repetition.root.main.ui.UiMainDialog
import cdu278.repetition.root.main.ui.UiMainTab
import cdu278.repetition.s.repository.FilteringRepetitionsItemsRepository
import cdu278.repetition.s.repository.RepetitionsRepository
import cdu278.state.State
import cdu278.ui.action.UiAction
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.router.pages.Pages
import com.arkivanov.decompose.router.pages.PagesNavigation
import com.arkivanov.decompose.router.pages.childPages
import com.arkivanov.decompose.router.pages.select
import com.arkivanov.decompose.router.slot.SlotNavigation
import com.arkivanov.decompose.router.slot.activate
import com.arkivanov.decompose.router.slot.childSlot
import com.arkivanov.decompose.router.slot.dismiss
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import cdu278.repetition.root.main.ui.UiMainMode.Default as ModeDefault
import cdu278.repetition.root.main.ui.UiMainMode.Selection as ModeSelection

@OptIn(ExperimentalDecomposeApi::class)
internal class MainComponent(
    context: IntervalsComponentContext,
    private val repetitionsRepository: RepetitionsRepository,
    goToRepetition: (repetitionId: Long) -> Unit,
    private val requestedTabFlow: MutableSharedFlow<MainTabConfig>
) : IntervalsComponentContext by context {

    private val listState =
        State(
            stateKeeper
                .consume("list", RepetitionListState.serializer())
                ?: Default
        )

    val newRepetitionFlowComponent =
        NewRepetitionFlowComponent(
            childContext("newRepetitionFlow"),
            repetitionsRepository,
            onCreated = { requestedTabFlow.emit(Active) },
        )

    private val dialogNavigation = SlotNavigation<MainDialogConfig>()

    private val dialogFlow =
        childSlot(
            source = dialogNavigation,
            handleBackButton = true,
        ) { config, componentContext ->
            when (config) {
                is Deletion ->
                    RepetitionsDeletionDialogComponent(
                        componentContext,
                        config.idsOfRepetitions,
                        repetitionsRepository,
                        dismiss = { dialogNavigation.dismiss() },
                        onDeleted = {
                            config.idsOfRepetitions.forEach {
                                repetitionNotifications.cancel(repetitionId = it)
                            }
                            listState.update { Default }
                        },
                    )
            }
        }.asStateFlow(lifecycle).map { slot ->
            slot.child?.let {
                UiMainDialog.Deletion(component = it.instance)
            }
        }

    private val coroutineScope = coroutineScope()

    @OptIn(ExperimentalDecomposeApi::class)
    private val tabsNavigation = PagesNavigation<MainTabConfig>()

    @OptIn(ExperimentalDecomposeApi::class)
    private val tabsFlow =
        childPages(
            source = tabsNavigation,
            serializer = MainTabConfig.serializer(),
            initialPages = {
                Pages(
                    items = MainTabConfig.entries.toList(),
                    selectedIndex = 0,
                )
            },
        ) { config, componentContext ->
            RepetitionListComponent(
                newContext(componentContext),
                FilteringRepetitionsItemsRepository(
                    original = repetitionsRepository,
                    predicate = when (config) {
                        Actual -> ActualRepetitionsFilter(currentTime)
                        Active -> ActiveRepetitionsFilter(currentTime)
                        Archive -> ArchivedRepetitionsFilter()
                    },
                ),
                state = listState,
                goToRepetition = goToRepetition,
            )
        }.asStateFlow(lifecycle).map { pages ->
            pages.items.mapIndexed { index, tab ->
                UiMainTab(
                    config = MainTabConfig.entries[index],
                    active = pages.selectedIndex == index,
                    activate = UiAction(key = index) {
                        tabsNavigation.select(index)
                    },
                    listComponent = tab.instance,
                )
            }
        }

    init {
        requestedTabFlow
            .onEach { configOfRequested ->
                val indexOfRequested = MainTabConfig.entries.indexOf(configOfRequested)
                withContext(Dispatchers.Main.immediate) {
                    tabsNavigation.select(indexOfRequested)
                }
            }
            .launchIn(coroutineScope)

        switchToActiveIfNoActual()
    }

    private fun switchToActiveIfNoActual() {
        coroutineScope.launch {
            tabsFlow.first()
                .find { it.config == Actual }!!
                .let {
                    if (!it.active) return@launch
                }
            repetitionsRepository
                .itemsFlow
                .take(1)
                .onEach { items ->
                    val noActualRepetitions =
                        items.none { ActualRepetitionsFilter(currentTime).test(it) }
                    if (noActualRepetitions) {
                        withContext(Dispatchers.Main.immediate) {
                            tabsNavigation.select(MainTabConfig.entries.indexOf(Active))
                        }
                    }
                }
                .launchIn(this)
        }
    }

    internal val uiModelFlow: StateFlow<UiMain> =
        listState.handle(coroutineScope, initialValue = UiMain()) { state ->
            combine(
                tabsFlow,
                dialogFlow,
            ) { tabs, dialog ->
                UiMain(
                    mode = when (state) {
                        is Default -> ModeDefault
                        is Selection ->
                            ModeSelection(
                                selectedCount = state.idsOfSelected.size,
                                delete = UiAction(key = state.idsOfSelected) {
                                    delete(state.idsOfSelected)
                                },
                                quitSelectionModel = UiAction(
                                    key = null,
                                    action = ::quitSelectionMode
                                ),
                                dialog = dialog,
                            )
                    },
                    tabs = tabs,
                )
            }
        }

    private fun quitSelectionMode() {
        listState.update { Default }
    }

    private fun delete(idsOfRepetitions: List<Long>) {
        dialogNavigation.activate(Deletion(idsOfRepetitions))
    }
}