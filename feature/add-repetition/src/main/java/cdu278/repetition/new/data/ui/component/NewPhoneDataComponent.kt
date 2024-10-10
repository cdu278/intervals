package cdu278.repetition.new.data.ui.component

import cdu278.computable.Computable
import cdu278.decompose.context.coroutineScope
import cdu278.repetition.new.data.ui.UiNewPhoneData
import cdu278.state.createState
import cdu278.ui.input.UiInput
import cdu278.ui.input.change.ChangeInput
import cdu278.ui.input.validated.Validated
import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.serialization.builtins.serializer

class NewPhoneDataComponent<Error>(
    context: ComponentContext,
    private val errors: Errors<Error>,
) : NewRepetitionDataComponent<Error>,
    ComponentContext by context {

    class Errors<out T>(
        val empty: Computable<T>,
    )

    private val state =
        createState(
            String.serializer(),
            initialValue = { "" }
        )

    override val dataFlow: StateFlow<Validated<String, Error>> =
        state.handle(
            coroutineScope(),
            initialValue = Validated.Invalid(errors.empty())
        ) { input ->
            flow {
                if (input.isBlank()) {
                    Validated.Invalid(errors.empty())
                } else {
                    Validated.Valid(input)
                }.let { emit(it) }
            }
        }

    private val changePhone = ChangeInput(state)

    val uiModelFlow: StateFlow<UiNewPhoneData> =
        state.handle(
            coroutineScope(),
            initialValue = UiNewPhoneData(
                phone =  UiInput(value = "", changePhone)
            )
        ) { input ->
            flowOf(
                UiNewPhoneData(
                    phone = UiInput(value = input, changePhone)
                )
            )
        }
}