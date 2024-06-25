package cdu278.repetition.new.data.ui.component

import cdu278.computable.Computable
import cdu278.decompose.context.coroutineScope
import cdu278.email.EmailRegex
import cdu278.repetition.new.data.ui.UiNewEmailData
import cdu278.state.State
import cdu278.ui.input.UiInput
import cdu278.ui.input.change.ChangeInput
import cdu278.ui.input.validated.Validated
import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.withContext
import kotlinx.serialization.builtins.serializer

class NewEmailDataComponent<Error>(
    context: ComponentContext,
    private val errors: Errors<Error>,
) : NewRepetitionDataComponent<Error>,
    ComponentContext by context {

    class Errors<out T>(
        val empty: Computable<T>,
        val notValid: Computable<T>,
    )

    private val inputState =
        State(
            stateKeeper
                .consume("input", String.serializer())
                ?: ""
        )

    private val coroutineScope = coroutineScope()

    override val dataFlow: StateFlow<Validated<String, Error>> =
        inputState.handle(
            coroutineScope,
            initialValue = Validated.Invalid(errors.empty())
        ) { input ->
            flow {
                when {
                    input.isEmpty() -> Validated.Invalid(errors.empty())
                    !input.valid() -> Validated.Invalid(errors.notValid())
                    else -> Validated.Valid(value = input)
                }.let { emit(it) }
            }
        }

    private suspend fun String.valid(): Boolean {
        return withContext(Dispatchers.Default) {
            val value = this@valid
            value.matches(EmailRegex)
        }
    }

    private val changeEmail = ChangeInput(inputState)

    val uiModelFlow: StateFlow<UiNewEmailData> =
        inputState.handle(
            coroutineScope,
            initialValue = UiNewEmailData(
                email = UiInput(value = "", changeEmail)
            )
        ) { input ->
            flowOf(
                UiNewEmailData(
                    email = UiInput(value = input, changeEmail),
                )
            )
        }
}