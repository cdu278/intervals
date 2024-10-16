package cdu278.repetition.ui.component

import cdu278.computable.Computable
import cdu278.decompose.context.coroutineScope
import cdu278.decompose.util.asStateFlow
import cdu278.intervals.ui.component.context.IntervalsComponentContext
import cdu278.phone.formatted.FormattedPhone
import cdu278.repetition.RepetitionState
import cdu278.repetition.RepetitionType
import cdu278.repetition.RepetitionType.Phone
import cdu278.repetition.dialog.ui.RepetitionDialogConfig
import cdu278.repetition.dialog.ui.RepetitionDialogConfig.ArchiveConfirmation
import cdu278.repetition.matching.RepetitionDataMatching
import cdu278.repetition.next.mapping.NextRepetitionDateMapping
import cdu278.repetition.repository.RepetitionRepository
import cdu278.repetition.stage.RepetitionStage
import cdu278.repetition.ui.RepetitionInput
import cdu278.repetition.ui.RepetitionInput.Forgotten
import cdu278.repetition.ui.UiRepetition
import cdu278.repetition.ui.UiRepetition.State.Checking.HintState
import cdu278.repetition.ui.UiRepetitionDialog
import cdu278.state.createState
import cdu278.state.prop
import cdu278.state.subtype
import cdu278.ui.action.UiAction
import cdu278.ui.input.UiInput
import cdu278.ui.input.change.ChangeInput
import com.arkivanov.decompose.router.slot.SlotNavigation
import com.arkivanov.decompose.router.slot.activate
import com.arkivanov.decompose.router.slot.childSlot
import com.arkivanov.decompose.router.slot.dismiss
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import cdu278.repetition.ui.RepetitionInput.Checking as CheckingInput
import cdu278.repetition.ui.UiRepetition.State as UiState
import cdu278.repetition.ui.UiRepetition.State.Checking.Message as CheckingMessage
import cdu278.repetition.ui.UiRepetition.State.Checking.Message.Failed as MessageFailed

class RepetitionComponent(
    componentContext: IntervalsComponentContext,
    private val repository: RepetitionRepository,
    private val dataMatching: RepetitionDataMatching,
    private val close: () -> Unit,
    private val repetitionDateMapping: NextRepetitionDateMapping = NextRepetitionDateMapping(),
    private val onChecked: suspend () -> Unit,
    private val onArchived: suspend () -> Unit,
) : IntervalsComponentContext by componentContext {

    private val state =
        createState(
            RepetitionInput.serializer(),
            initialValue = ::CheckingInput
        )

    private val coroutineScope = coroutineScope()

    private val changeData
        get() = ChangeInput(
            state
                .subtype(CheckingInput::class)
                .prop(CheckingInput::data) { copy(data = it) }
        )

    private fun CheckingInput.error(type: RepetitionType): CheckingMessage? {
        return if (data.isEmpty()) {
            CheckingMessage.DataEmpty(type)
        } else {
            null
        }
    }

    private val checkingFlow = MutableStateFlow(false)

    private val failedFlow = MutableStateFlow(false)

    private val dialogNavigation = SlotNavigation<RepetitionDialogConfig>()

    private val dialogFlow =
        childSlot(
            source = dialogNavigation,
            handleBackButton = true,
        ) { config, componentContext ->
            when (config) {
                is ArchiveConfirmation ->
                    ArchiveConfirmationDialogComponent(
                        componentContext,
                    )
            }
        }.asStateFlow(lifecycle).map { slot ->
            slot.child?.let {
                when (it.configuration) {
                    is ArchiveConfirmation ->
                        UiRepetitionDialog.ArchiveConfirmation(
                            component = it.instance,
                            dismiss = UiAction(key = null) { dialogNavigation.dismiss() },
                            confirm = UiAction(key = null, action = ::archive),
                        )
                }
            }
        }

    internal val uiModelFlow: StateFlow<UiRepetition?> =
        state.handle(coroutineScope, initialValue = null) { input ->
            combine(
                repository.flow,
                checkingFlow,
                failedFlow,
                dialogFlow,
            ) { repetition, checking, failed, dialog ->
                UiRepetition(
                    repetition.type,
                    repetition.label,
                    state = when (input) {
                        is Forgotten -> UiState.Forgotten
                        is CheckingInput -> {
                            val hintState =
                                repetition.hint?.let { hint ->
                                    if (input.hintShown) {
                                        HintState.Shown(hint)
                                    } else {
                                        HintState.Hidden
                                    }
                                }
                            when (val state = repetition.state) {
                                is RepetitionState.Repetition ->
                                    if (currentTime() >= state.date) {
                                        val inputError = input.error(repetition.type)
                                        UiState.Checking(
                                            mode = UiState.Checking.Mode.Repetition,
                                            data = UiInput(input.data, changeData),
                                            message = if (failed) MessageFailed else inputError,
                                            valid = inputError == null,
                                            hintState,
                                            inProgress = checking,
                                        )
                                    } else {
                                        UiState.RepetitionAt(
                                            date = with(repetitionDateMapping) {
                                                state.date.toUiModel()
                                            }
                                        )
                                    }

                                is RepetitionState.Forgotten -> {
                                    val inputError = input.error(repetition.type)
                                    UiState.Checking(
                                        mode = UiState.Checking.Mode.Remembering,
                                        data = UiInput(input.data, changeData),
                                        message = if (failed) MessageFailed else inputError,
                                        valid = inputError == null,
                                        hintState,
                                        inProgress = checking,
                                    )
                                }
                            }
                        }
                    },
                    dialog = dialog,
                )
            }
        }

    fun showHint() {
        coroutineScope.launch {
            repository.updateState { it.state.withHintShown(true) }
            state
                .subtype(RepetitionInput.Checking::class)
                .update { it.copy(hintShown = true) }
        }
    }

    fun check() {
        coroutineScope.launch {
            checkingFlow.value = true
            failedFlow.value = false

            val rawData = (state.value as CheckingInput).data
            val repetition = repository.get()
            val data =
                when (repetition.type) {
                    Phone -> FormattedPhone(rawData)
                    else -> Computable { rawData }
                }
            if (with(dataMatching) { repetition.data matches data }) {
                val nextStage =
                    when (val state = repetition.state) {
                        is RepetitionState.Repetition ->
                            if (state.hintShown) {
                                state.stage
                            } else {
                                state.stage.next()
                            }
                        is RepetitionState.Forgotten -> RepetitionStage.Initial
                    }

                val nextRepetition = repetitionIntervals.next(nextStage)

                repository.updateState {
                    RepetitionState.Repetition(
                        date = nextRepetition,
                        nextStage,
                        hintShown = false
                    )
                }

                repetitionNotifications.schedule(repetition.id, nextRepetition)

                onChecked()
            } else {
                changeData("")
                failedFlow.value = true
            }

            checkingFlow.value = false
        }
    }

    fun forget() {
        dialogNavigation.activate(ArchiveConfirmation)
    }

    private fun archive() {
        coroutineScope.launch {
            repository.updateState { RepetitionState.Forgotten() }
            state.update { Forgotten }
            onArchived()
        }
        dialogNavigation.dismiss()
    }

    fun close() {
        this.close.invoke()
    }
}
