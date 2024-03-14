package midget17468.memo.decompose.component

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.doOnStart
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import midget17468.compose.context.coroutineScope
import midget17468.datetime.currentTime
import midget17468.input.Input
import midget17468.input.prop
import midget17468.input.subtype
import midget17468.memo.mapping.ui.NextRepetitionDateMapping
import midget17468.memo.model.domain.EmptyPasswordMessageOwner
import midget17468.memo.model.domain.RepetitionStage
import midget17468.memo.model.domain.RepetitionState
import midget17468.memo.model.input.RepetitionInput
import midget17468.memo.model.ui.UiRepetition
import midget17468.memo.model.ui.UiRepetition.State.Checking.HintState
import midget17468.memo.repetitions.SpacedRepetitions
import midget17468.memo.repetitions.notifications.RepetitionsNotifications
import midget17468.memo.repository.MemoRepository
import midget17468.model.ui.ChangeInput
import midget17468.model.ui.Loadable
import midget17468.model.ui.UiInput
import midget17468.memo.model.input.RepetitionInput.Checking as CheckingInput
import midget17468.memo.model.ui.UiRepetition.State as UiState

class RepetitionComponent<Errors : EmptyPasswordMessageOwner>(
    componentContext: ComponentContext,
    private val memoId: Int,
    private val repository: MemoRepository,
    private val repetitionNotifications: RepetitionsNotifications,
    private val errors: Errors,
    private val spacedRepetitions: SpacedRepetitions = SpacedRepetitions(),
    private val currentTime: () -> LocalDateTime = { Clock.System.currentTime() },
    private val repetitionDateMapping: NextRepetitionDateMapping = NextRepetitionDateMapping(),
    private val close: () -> Unit,
) : ComponentContext by componentContext {

    private val input: Input<RepetitionInput> =
        Input(
            stateKeeper.consume("state", RepetitionInput.serializer())
                ?: CheckingInput()
        )

    private val coroutineScope = coroutineScope()

    init {
        stateKeeper.register("state", RepetitionInput.serializer()) { input.value }

        doOnStart {
            coroutineScope.launch { repetitionNotifications.remove(memoId) }
        }
    }

    private val changeData
        get() = ChangeInput(
            input
                .subtype(CheckingInput::class)
                .prop(CheckingInput::data) { copy(data = it) }
        )

    private val CheckingInput.error: String?
        get() = if (data.isEmpty()) {
            errors.emptyPassword()
        } else {
            null
        }

    internal val uiModelFlow: StateFlow<Loadable<UiRepetition>> =
        input.handle(coroutineScope, initialValue = Loadable.Loading) { input ->
            repository.flowById(memoId).map { memo ->
                Loadable.Loaded(
                    UiRepetition(
                        memo.type,
                        memo.label,
                        state = when (input) {
                            is RepetitionInput.Forgotten ->
                                UiState.Forgotten
                            is CheckingInput -> {
                                val hintState =
                                    memo.hint?.let { hint ->
                                        if (input.hintShown) {
                                            HintState.Shown(hint)
                                        } else {
                                            HintState.Hidden
                                        }
                                    }
                                when (val state = memo.repetitionState) {
                                    is RepetitionState.Repetition ->
                                        if (currentTime() >= state.date) {
                                            UiState.Checking(
                                                mode = UiState.Checking.Mode.Repetition,
                                                data = UiInput(input.data, changeData),
                                                error = input.error,
                                                hintState,
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
            repository.update(
                memoId,
                updatedState = { it.repetitionState.withHintShown(true) }
            )
            input
                .subtype(RepetitionInput.Checking::class)
                .update { it.copy(hintShown = true) }
        }
    }

    fun check() {
        coroutineScope.launch {
            val data = (input.value as CheckingInput).data
            val memo = repository.findById(memoId)
            if (memo.memoData.matches(data)) {
                val nextStage =
                    when (val state = memo.repetitionState) {
                        is RepetitionState.Repetition ->
                            if (state.hintShown) {
                                state.stage
                            } else {
                                state.stage.next()
                            }
                        is RepetitionState.Forgotten -> RepetitionStage.Initial
                    }

                val nextRepetition = spacedRepetitions.next(nextStage)

                repository.update(
                    memoId,
                    updatedState = {
                        RepetitionState.Repetition(
                            date = nextRepetition,
                            nextStage,
                            hintShown = false
                        )
                    }
                )

                repetitionNotifications.schedule(memoId, nextRepetition)
            } else {
                changeData("")
            }
        }
    }

    fun forget() {
        coroutineScope.launch {
            repository.update(
                memoId,
                updatedState = { RepetitionState.Forgotten() }
            )
            input.update { RepetitionInput.Forgotten }
        }
    }

    fun close() {
        this.close.invoke()
    }
}