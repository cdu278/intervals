package midget17468.memo.decompose.component

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.childContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import midget17468.compose.context.coroutineScope
import midget17468.decompose.instance.retainedCoroutineScope
import midget17468.hash.algorithm.HashAlgorithm
import midget17468.state.Input
import midget17468.state.prop
import midget17468.memo.Memo
import midget17468.memo.data.MemoData
import midget17468.memo.model.input.AddMemoInput
import midget17468.memo.model.domain.EmptyLabelMessageOwner
import midget17468.memo.model.domain.EmptyPasswordMessageOwner
import midget17468.memo.model.domain.PasswordsDontMatchMessageOwner
import midget17468.memo.model.ui.UiNewMemo
import midget17468.memo.model.ui.UiNewMemoData
import midget17468.memo.repetitions.notifications.RepetitionsNotifications
import midget17468.memo.model.domain.MemoType
import midget17468.memo.model.domain.MemoType.Password
import midget17468.memo.model.domain.RepetitionStage
import midget17468.memo.model.domain.RepetitionState
import midget17468.model.ui.ChangeInput
import midget17468.model.ui.UiInput
import midget17468.model.ui.Validated.Invalid
import midget17468.model.ui.Validated.Valid
import midget17468.memo.repetitions.SpacedRepetitions
import midget17468.memo.repository.MemoRepository

class NewMemoEditorComponent<Errors>(
    context: ComponentContext,
    private val type: MemoType,
    private val errors: Errors,
    private val spacedRepetitions: SpacedRepetitions,
    private val repetitionNotifications: RepetitionsNotifications,
    private val repository: MemoRepository,
    private val hashAlgorithm: HashAlgorithm,
    private val close: () -> Unit,
) : ComponentContext by context
        where Errors : EmptyPasswordMessageOwner,
              Errors : PasswordsDontMatchMessageOwner,
              Errors : EmptyLabelMessageOwner {

    private val input =
        Input(
            stateKeeper.consume("passwordEditor", AddMemoInput.serializer())
                ?: AddMemoInput()
        )

    init {
        stateKeeper.register("passwordEditor", AddMemoInput.serializer()) { input.value }
    }

    private val changeLabel =
        ChangeInput(input.prop(AddMemoInput::label) { copy(label = it) })

    private val changeHint =
        ChangeInput(input.prop(AddMemoInput::hint) { copy(hint = it) })

    private val dataUiModel: UiNewMemoData = run {
        val childContext = childContext("data")
        when (type) {
            Password ->
                UiNewMemoData.Password(
                    NewPasswordDataComponent(childContext, errors)
                )
        }
    }

    val uiModelFlow: StateFlow<UiNewMemo> =
        input.handle(
            coroutineScope(),
            initialValue = UiNewMemo(
                label = UiInput(value = input.value.label, changeLabel),
                hint = UiInput(value = input.value.hint, changeHint),
                data = dataUiModel,
            ),
        ) { input ->
            dataUiModel.component.dataFlow.map { data ->
                UiNewMemo(
                    label = UiInput(value = input.label, changeLabel),
                    hint = UiInput(value = input.hint, changeHint),
                    data = dataUiModel,
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
            val input = input.value
            val data = (dataUiModel.component.dataFlow.value as Valid<String>).value
            val stage = RepetitionStage.Initial
            val nextRepetition = spacedRepetitions.next(stage)
            val id =
                repository.create(
                    Memo(
                        id = 0,
                        type,
                        input.label,
                        memoData = when (type) {
                            Password ->
                                MemoData.Hashed(
                                    hash = with(hashAlgorithm) { data.hash() },
                                    hashAlgorithm,
                                )
                        },
                        repetitionState = RepetitionState.Repetition(
                            date = nextRepetition,
                            stage,
                            hintShown = false,
                        ),
                        hint = input.hint.takeIf { it.isNotBlank() },
                    )
                )

            repetitionNotifications.schedule(id, date = nextRepetition)

            withContext(Dispatchers.Main.immediate) { close() }
        }
    }
}