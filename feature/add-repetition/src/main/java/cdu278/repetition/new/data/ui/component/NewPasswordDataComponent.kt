package cdu278.repetition.new.data.ui.component

import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOf
import cdu278.decompose.context.coroutineScope
import cdu278.state.State
import cdu278.state.prop
import cdu278.password.ui.ConfirmedPasswordInput
import cdu278.repetition.new.data.ui.UiNewPasswordData
import cdu278.repetition.new.error.owner.EmptyPasswordErrorOwner
import cdu278.repetition.new.error.owner.PasswordsDontMatchErrorOwner
import cdu278.ui.input.UiInput
import cdu278.ui.input.change.ChangeInput
import cdu278.ui.input.validated.Validated

class NewPasswordDataComponent<Errors>(
    context: ComponentContext,
    private val errors: Errors,
) : NewRepetitionDataComponent,
    ComponentContext by context
        where Errors : EmptyPasswordErrorOwner,
              Errors : PasswordsDontMatchErrorOwner {

    private val input =
        State(
            stateKeeper.consume("passwordData", ConfirmedPasswordInput.serializer())
                ?: ConfirmedPasswordInput()
        )

    init {
        stateKeeper.register("passwordData", ConfirmedPasswordInput.serializer()) { input.value }
    }

    private val coroutineScope = coroutineScope()

    override val dataFlow: StateFlow<Validated<String>> =
        input.handle(coroutineScope, initialValue = Validated.Invalid()) { input ->
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