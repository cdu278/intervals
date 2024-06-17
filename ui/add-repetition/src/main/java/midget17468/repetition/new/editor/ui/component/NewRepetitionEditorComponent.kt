package midget17468.repetition.new.editor.ui.component

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.childContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import midget17468.decompose.context.coroutineScope
import midget17468.decompose.instance.retainedCoroutineScope
import midget17468.hash.s.Hashes
import midget17468.repetition.Repetition
import midget17468.repetition.RepetitionData
import midget17468.repetition.RepetitionState
import midget17468.repetition.RepetitionType
import midget17468.repetition.RepetitionType.Password
import midget17468.repetition.new.data.ui.UiNewRepetitionData
import midget17468.repetition.new.data.ui.component.NewPasswordDataComponent
import midget17468.repetition.new.error.owner.EmptyLabelErrorOwner
import midget17468.repetition.new.error.owner.EmptyPasswordErrorOwner
import midget17468.repetition.new.error.owner.PasswordsDontMatchErrorOwner
import midget17468.repetition.new.ui.NewRepetitionInput
import midget17468.repetition.new.ui.UiNewRepetition
import midget17468.repetition.notification.s.RepetitionsNotifications
import midget17468.repetition.s.repository.RepetitionsRepository
import midget17468.repetition.spaced.SpacedRepetitions
import midget17468.repetition.stage.RepetitionStage
import midget17468.state.State
import midget17468.state.prop
import midget17468.ui.input.UiInput
import midget17468.ui.input.change.ChangeInput
import midget17468.ui.input.validated.Validated.Invalid
import midget17468.ui.input.validated.Validated.Valid

class NewRepetitionEditorComponent<Errors>(
    context: ComponentContext,
    private val type: RepetitionType,
    private val errors: Errors,
    private val spacedRepetitions: SpacedRepetitions,
    private val repetitionNotifications: RepetitionsNotifications,
    private val repository: RepetitionsRepository,
    private val hashes: Hashes,
    private val close: () -> Unit,
) : ComponentContext by context
        where Errors : EmptyPasswordErrorOwner,
              Errors : PasswordsDontMatchErrorOwner,
              Errors : EmptyLabelErrorOwner {

    private val input =
        State(
            stateKeeper.consume("passwordEditor", NewRepetitionInput.serializer())
                ?: NewRepetitionInput()
        )

    init {
        stateKeeper.register("passwordEditor", NewRepetitionInput.serializer()) { input.value }
    }

    private val changeLabel =
        ChangeInput(input.prop(NewRepetitionInput::label) { copy(label = it) })

    private val changeHint =
        ChangeInput(input.prop(NewRepetitionInput::hint) { copy(hint = it) })

    private val dataUiModel: UiNewRepetitionData = run {
        val childContext = childContext("data")
        when (type) {
            Password ->
                UiNewRepetitionData.Password(
                    NewPasswordDataComponent(childContext, errors)
                )
        }
    }

    private val _savingFlow = MutableStateFlow(false)
    private val savingFlow
        get() = _savingFlow

    val uiModelFlow: StateFlow<UiNewRepetition> =
        input.handle(
            coroutineScope(),
            initialValue = UiNewRepetition(
                label = UiInput(value = input.value.label, changeLabel),
                hint = UiInput(value = input.value.hint, changeHint),
                data = dataUiModel,
            ),
        ) { input ->
            combine(
                dataUiModel.component.dataFlow,
                savingFlow
            ) { data, saving ->
                UiNewRepetition(
                    label = UiInput(value = input.label, changeLabel),
                    hint = UiInput(value = input.hint, changeHint),
                    data = dataUiModel,
                    saving = saving,
                    error = if (input.label.isBlank()) {
                        errors.emptyLabel()
                    } else {
                        when (data) {
                            is Valid -> null
                            is Invalid -> data.error
                        }
                    },
                )
            }
        }

    fun close() {
        close.invoke()
    }

    fun save() {
        retainedCoroutineScope.launch {
            _savingFlow.value = true

            val input = input.value
            val data = (dataUiModel.component.dataFlow.value as Valid<String>).value
            val stage = RepetitionStage.Initial
            val nextRepetition = spacedRepetitions.next(stage)
            val id =
                repository.create(
                    Repetition(
                        id = 0,
                        input.label,
                        type,
                        data = when (type) {
                            Password -> RepetitionData.Hashed(hashes.of(data))
                        },
                        state = RepetitionState.Repetition(
                            date = nextRepetition,
                            stage,
                            hintShown = false,
                        ),
                        hint = input.hint.takeIf { it.isNotBlank() },
                    )
                )

            repetitionNotifications.schedule(id, date = nextRepetition)

            _savingFlow.value = false
            withContext(Dispatchers.Main.immediate) { close() }
        }
    }
}