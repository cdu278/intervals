package cdu278.repetition.new.editor.ui.component

import cdu278.computable.Computable
import cdu278.decompose.context.coroutineScope
import cdu278.decompose.instance.retainedCoroutineScope
import cdu278.intervals.ui.component.context.IntervalsComponentContext
import cdu278.phone.formatted.FormattedPhone
import cdu278.repetition.Repetition
import cdu278.repetition.RepetitionData
import cdu278.repetition.RepetitionState
import cdu278.repetition.RepetitionType
import cdu278.repetition.RepetitionType.Email
import cdu278.repetition.RepetitionType.Password
import cdu278.repetition.RepetitionType.Phone
import cdu278.repetition.RepetitionType.Pin
import cdu278.repetition.new.data.ui.UiNewRepetitionData
import cdu278.repetition.new.data.ui.component.NewEmailDataComponent
import cdu278.repetition.new.data.ui.component.NewPasswordDataComponent
import cdu278.repetition.new.data.ui.component.NewPhoneDataComponent
import cdu278.repetition.new.ui.NewRepetitionInput
import cdu278.repetition.new.ui.UiNewRepetition
import cdu278.repetition.s.repository.RepetitionsRepository
import cdu278.repetition.stage.RepetitionStage
import cdu278.state.createState
import cdu278.state.prop
import cdu278.ui.input.UiInput
import cdu278.ui.input.change.ChangeInput
import cdu278.ui.input.validated.Validated.Invalid
import cdu278.ui.input.validated.Validated.Valid
import com.arkivanov.decompose.childContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import cdu278.repetition.new.ui.UiNewRepetition.Error as UiError

class NewRepetitionEditorComponent(
    context: IntervalsComponentContext,
    private val type: RepetitionType,
    private val repository: RepetitionsRepository,
    private val close: () -> Unit,
    private val onCreated: suspend () -> Unit,
) : IntervalsComponentContext by context {

    private val state =
        createState(
            NewRepetitionInput.serializer(),
            initialValue = ::NewRepetitionInput,
        )

    private val changeLabel =
        ChangeInput(state.prop(NewRepetitionInput::label) { copy(label = it) })

    private val changeHint =
        ChangeInput(state.prop(NewRepetitionInput::hint) { copy(hint = it) })

    private val dataUiModel: UiNewRepetitionData<UiError> = run {
        val newSecretComponent by lazy {
            NewPasswordDataComponent(
                childContext("data"),
                NewPasswordDataComponent.Errors(
                    emptyPassword = { UiError.EmptyData },
                    passwordsDontMatch = { UiError.PasswordsDontMatch },
                ),
            )
        }
        val newEmailComponent by lazy {
            NewEmailDataComponent(
                childContext("data"),
                NewEmailDataComponent.Errors(
                    empty = { UiError.EmptyData },
                    notValid = { UiError.InvalidData },
                ),
            )
        }
        val newPhoneComponent by lazy {
            NewPhoneDataComponent(
                childContext("data"),
                NewPhoneDataComponent.Errors(
                    empty = { UiError.EmptyData },
                ),
            )
        }
        when (type) {
            Password -> UiNewRepetitionData.Password(newSecretComponent)
            Pin -> UiNewRepetitionData.Pin(newSecretComponent)
            Email -> UiNewRepetitionData.Email(newEmailComponent)
            Phone -> UiNewRepetitionData.Phone(newPhoneComponent)
        }
    }

    private val _savingFlow = MutableStateFlow(false)
    private val savingFlow
        get() = _savingFlow

    val uiModelFlow: StateFlow<UiNewRepetition> =
        state.handle(
            coroutineScope(),
            initialValue = UiNewRepetition(
                label = UiInput(value = state.value.label, changeLabel),
                hint = UiInput(value = state.value.hint, changeHint),
                data = dataUiModel,
                type = Password,
            ),
        ) { input ->
            combine(
                dataUiModel.component.dataFlow,
                savingFlow
            ) { data, saving ->
                UiNewRepetition(
                    label = UiInput(value = input.label, changeLabel),
                    type = type,
                    hint = UiInput(value = input.hint, changeHint),
                    data = dataUiModel,
                    saving = saving,
                    error = when (data) {
                        is Valid -> {
                            val label = input.label.trim()
                            if (label.isEmpty()) {
                                UiError.EmptyLabel
                            } else if (repository.findByLabel(label) != null) {
                                UiError.LabelExists
                            } else {
                                null
                            }
                        }

                        is Invalid -> data.error
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

            val input = state.value
            val dataString = (dataUiModel.component.dataFlow.value as Valid<String>).value
            val data =
                when (type) {
                    Phone -> FormattedPhone(dataString)
                    else -> Computable { dataString }
                }
            val stage = RepetitionStage.Initial
            val nextRepetition = repetitionIntervals.next(stage)
            val id =
                repository.create(
                    Repetition(
                        id = 0,
                        label = input.label.trim(),
                        type,
                        data = when (type) {
                            Password, Pin, Email, Phone -> RepetitionData.Hashed(hashes.of(data))
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

            onCreated()

            _savingFlow.value = false
            withContext(Dispatchers.Main.immediate) { close() }
        }
    }
}