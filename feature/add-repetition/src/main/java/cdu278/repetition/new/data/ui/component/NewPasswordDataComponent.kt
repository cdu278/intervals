package cdu278.repetition.new.data.ui.component

import cdu278.computable.Computable
import cdu278.decompose.context.coroutineScope
import cdu278.password.ui.ConfirmedPasswordInput
import cdu278.repetition.new.data.ui.UiNewPasswordData
import cdu278.state.State
import cdu278.state.prop
import cdu278.ui.input.UiInput
import cdu278.ui.input.change.ChangeInput
import cdu278.ui.input.validated.Validated
import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOf

class NewPasswordDataComponent<Error>(
    context: ComponentContext,
    private val errors: Errors<Error>,
) : NewRepetitionDataComponent<Error>,
    ComponentContext by context {

    class Errors<out T>(
        val emptyPassword: Computable<T>,
        val passwordsDontMatch: Computable<T>,
    )

    private val input =
        State(
            stateKeeper.consume("passwordData", ConfirmedPasswordInput.serializer())
                ?: ConfirmedPasswordInput()
        )

    init {
        stateKeeper.register("passwordData", ConfirmedPasswordInput.serializer()) { input.value }
    }

    private val coroutineScope = coroutineScope()

    override val dataFlow: StateFlow<Validated<String, Error>> =
        input.handle(
            coroutineScope,
            initialValue = Validated.Invalid(errors.emptyPassword()),
        ) { input ->
            with(input) {
                when {
                    password.isBlank() -> Validated.Invalid(errors.emptyPassword())
                    password != passwordConfirmation ->
                        Validated.Invalid(errors.passwordsDontMatch())
                    else -> Validated.Valid(input.password)
                }.let(::flowOf)
            }
        }

    private val changePassword =
        ChangeInput(input.prop(ConfirmedPasswordInput::password) { copy(password = it) })

    private val changePasswordConfirmation =
        ChangeInput(input.prop(ConfirmedPasswordInput::passwordConfirmation) {
            copy(passwordConfirmation = it)
        })

    val uiModelFlow: StateFlow<UiNewPasswordData> =
        input.handle(
            coroutineScope,
            initialValue = UiNewPasswordData(
                password = UiInput(input.value.password, changePassword),
                passwordConfirmation = UiInput(
                    input.value.passwordConfirmation,
                    changePasswordConfirmation
                ),
            )
        ) { input ->
            flowOf(
                UiNewPasswordData(
                    password = UiInput(input.password, changePassword),
                    passwordConfirmation = UiInput(
                        input.passwordConfirmation,
                        changePasswordConfirmation
                    ),
                )
            )
        }
}