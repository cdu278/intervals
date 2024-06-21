package cdu278.repetition.root.main.ui.component

import cdu278.decompose.context.coroutineScope
import cdu278.intervals.ui.component.context.IntervalsComponentContext
import cdu278.intervals.ui.component.context.childContext
import cdu278.repetition.list.ui.RepetitionListState
import cdu278.repetition.list.ui.RepetitionListState.Default
import cdu278.repetition.list.ui.RepetitionListState.Selection
import cdu278.repetition.list.ui.component.RepetitionListComponent
import cdu278.repetition.new.flow.ui.component.NewRepetitionFlowComponent
import cdu278.repetition.root.main.ui.UiMain
import cdu278.repetition.s.repository.RepetitionsRepository
import cdu278.state.State
import cdu278.ui.action.UiAction
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch

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

    private val coroutineScope = coroutineScope()

    val uiModelFlow: StateFlow<UiMain> =
        listState.handle(coroutineScope, initialValue = UiMain.Default) { state ->
            when (state) {
                is Default -> UiMain.Default
                is Selection ->
                    UiMain.Selection(
                        selectedCount = state.idsOfSelected.size,
                        delete = UiAction(key = state.idsOfSelected) {
                            delete(state.idsOfSelected)
                        },
                        quitSelectionModel = UiAction(key = null, action = ::quitSelectionMode),
                    )
            }.let { flowOf(it) }
        }

    private fun delete(idsOfRepetitions: List<Long>) {
        coroutineScope.launch {
            idsOfRepetitions
                .map { id ->
                    async {
                        repetitionsRepository
                            .repetitionRepository(id)
                            .delete()
                    }
                }
                .joinAll()
            listState.update { Default }
        }
    }

    private fun quitSelectionMode() {
        listState.update { Default }
    }
}