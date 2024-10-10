package cdu278.repetition.new.data.ui.component

import cdu278.computable.Computable
import cdu278.decompose.context.coroutineScope
import cdu278.email.EmailRegex
import cdu278.repetition.new.data.ui.UiNewEmailData
import cdu278.state.createState
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

    private val state =
        createState(
            String.serializer(),
            initialValue = { "" }
        )

    private val coroutineScope = coroutineScope()

    override val dataFlow: StateFlow<Validated<String, Error>> =
        state.handle(
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

    private val changeEmail = ChangeInput(state)

    val uiModelFlow: StateFlow<UiNewEmailData> =
        state.handle(
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