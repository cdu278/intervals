package midget17468.repetition.ui.component

import cdu278.intervals.repetition.s.repository.RoomRepetitionsRepository
import cdu278.intervals.ui.component.context.IntervalsComponentContext
import com.arkivanov.essenty.instancekeeper.getOrCreate
import com.arkivanov.essenty.lifecycle.doOnStart
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import midget17468.datetime.currentTime
import midget17468.decompose.context.coroutineScope
import midget17468.loadable.ui.Loadable
import midget17468.repetition.RepetitionState
import midget17468.repetition.matching.RepetitionDataMatching
import midget17468.repetition.new.error.owner.EmptyPasswordErrorOwner
import midget17468.repetition.next.mapping.NextRepetitionDateMapping
import midget17468.repetition.s.repository.RepetitionsRepository
import midget17468.repetition.s.repository.RepetitionsRepositoryInstance
import midget17468.repetition.stage.RepetitionStage
import midget17468.repetition.ui.RepetitionInput
import midget17468.repetition.ui.RepetitionInput.Forgotten
import midget17468.repetition.ui.UiRepetition
import midget17468.repetition.ui.UiRepetition.State.Checking.HintState
import midget17468.state.State
import midget17468.state.prop
import midget17468.state.subtype
import midget17468.ui.input.UiInput
import midget17468.ui.input.change.ChangeInput
import midget17468.repetition.ui.RepetitionInput.Checking as CheckingInput
import midget17468.repetition.ui.UiRepetition.State as UiState

class RepetitionComponent<Errors : EmptyPasswordErrorOwner>(
    componentContext: IntervalsComponentContext,
    private val repetitionId: Long,
    private val errors: Errors,
    private val dataMatching: RepetitionDataMatching,
    private val close: () -> Unit,
    private val currentTime: () -> LocalDateTime = { Clock.System.currentTime() },
    private val repetitionDateMapping: NextRepetitionDateMapping = NextRepetitionDateMapping(),
) : IntervalsComponentContext by componentContext {

    private val repetitionsRepository: RepetitionsRepository =
        instanceKeeper.getOrCreate {
            RepetitionsRepositoryInstance(
                repository = {
                    RoomRepetitionsRepository(
                        db.repetitionsDao,
                        db.repetitionDao,
                    )
                },
            )
        }

    private val repository
        get() = repetitionsRepository.repetitionRepository(repetitionId)

    private val state: State<RepetitionInput> =
        State(
            stateKeeper.consume("state", RepetitionInput.serializer())
                ?: CheckingInput()
        )

    private val coroutineScope = coroutineScope()

    init {
        stateKeeper.register("state", RepetitionInput.serializer()) { state.value }

        doOnStart {
            coroutineScope.launch { repetitionNotifications.remove(repetitionId) }
        }
    }

    private val changeData
        get() = ChangeInput(
            state
                .subtype(CheckingInput::class)
                .prop(CheckingInput::data) { copy(data = it) }
        )

    private val CheckingInput.error: String?
        get() = if (data.isEmpty()) {
            errors.emptyPassword()
        } else {
            null
        }

    private val checkingFlow = MutableStateFlow(false)

    internal val uiModelFlow: StateFlow<Loadable<UiRepetition>> =
        state.handle(coroutineScope, initialValue = Loadable.Loading) { input ->
            combine(
                repetitionsRepository.flowById(repetitionId),
                checkingFlow
            ) { repetition, checking ->
                Loadable.Loaded(
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
                                            UiState.Checking(
                                                mode = UiState.Checking.Mode.Repetition,
                                                data = UiInput(input.data, changeData),
                                                error = input.error,
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

                                    is RepetitionState.Forgotten ->
                                        UiState.Checking(
                                            mode = UiState.Checking.Mode.Remembering,
                                            data = UiInput(input.data, changeData),
                                            error = input.error,
                                            hintState,
                                            inProgress = checking,
                                        )
                                }
                            }
                        }
                    )
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

            val data = (state.value as CheckingInput).data
            val repetition = repetitionsRepository.findById(repetitionId)
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

                val nextRepetition = spacedRepetitions.next(nextStage)

                repository.updateState {
                    RepetitionState.Repetition(
                        date = nextRepetition,
                        nextStage,
                        hintShown = false
                    )
                }

                repetitionNotifications.schedule(repetitionId, nextRepetition)
            } else {
                changeData("")
            }

            checkingFlow.value = false
        }
    }

    fun forget() {
        coroutineScope.launch {
            repository.updateState { RepetitionState.Forgotten() }
            state.update { Forgotten }
        }
    }

    fun close() {
        this.close.invoke()
    }
}