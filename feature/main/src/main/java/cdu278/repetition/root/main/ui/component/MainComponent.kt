package cdu278.repetition.root.main.ui.component

import cdu278.decompose.context.coroutineScope
import cdu278.decompose.util.asStateFlow
import cdu278.intervals.ui.component.context.IntervalsComponentContext
import cdu278.intervals.ui.component.context.childContext
import cdu278.repetition.deletion.dialog.ui.component.RepetitionsDeletionDialogComponent
import cdu278.repetition.list.ui.RepetitionListState
import cdu278.repetition.list.ui.RepetitionListState.Default
import cdu278.repetition.list.ui.RepetitionListState.Selection
import cdu278.repetition.list.ui.component.RepetitionListComponent
import cdu278.repetition.new.flow.ui.component.NewRepetitionFlowComponent
import cdu278.repetition.root.main.ui.MainDialogConfig
import cdu278.repetition.root.main.ui.MainDialogConfig.Deletion
import cdu278.repetition.root.main.ui.UiMain
import cdu278.repetition.root.main.ui.UiMainDialog
import cdu278.repetition.s.repository.RepetitionsRepository
import cdu278.state.State
import cdu278.ui.action.UiAction
import com.arkivanov.decompose.router.slot.SlotNavigation
import com.arkivanov.decompose.router.slot.activate
import com.arkivanov.decompose.router.slot.childSlot
import com.arkivanov.decompose.router.slot.dismiss
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map

class MainComponent(
    context: IntervalsComponentContext,
    private val repetitionsRepository: RepetitionsRepository,
    repeat: (repetitionId: Long) -> Unit,
) : IntervalsComponentContext by context {

    private val listState =
        State(
            stateKeeper
                .consume("list", RepetitionListState.serializer())
                ?: Default
        )

    val repetitionListComponent =
        RepetitionListComponent(
            childContext("passwordList"),
            repetitionsRepository,
            listState,
            repeat,
        )

    val newRepetitionFlowComponent =
        NewRepetitionFlowComponent(
            childContext("newRepetitionFlow"),
            repetitionsRepository,
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

    internal val uiModelFlow: StateFlow<UiMain> =
        listState.handle(coroutineScope(), initialValue = UiMain.Default) { state ->
            dialogFlow.map { dialog ->
                when (state) {
                    is Default -> UiMain.Default
                    is Selection ->
                        UiMain.Selection(
                            selectedCount = state.idsOfSelected.size,
                            delete = UiAction(key = state.idsOfSelected) {
                                delete(state.idsOfSelected)
                            },
                            quitSelectionModel = UiAction(key = null, action = ::quitSelectionMode),
                            dialog = dialog,
                        )
                }
            }
        }

    private fun quitSelectionMode() {
        listState.update { Default }
    }

    private fun delete(idsOfRepetitions: List<Long>) {
        dialogNavigation.activate(Deletion(idsOfRepetitions))
    }
}