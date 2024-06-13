package midget17468.memo.decompose.component

import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOf
import midget17468.compose.context.coroutineScope
import midget17468.state.Input
import midget17468.state.prop
import midget17468.memo.model.input.ConfirmedPasswordInput
import midget17468.memo.model.domain.EmptyPasswordMessageOwner
import midget17468.memo.model.domain.PasswordsDontMatchMessageOwner
import midget17468.memo.model.ui.UiNewPasswordData
import midget17468.model.ui.ChangeInput
import midget17468.model.ui.UiInput
import midget17468.model.ui.Validated

class NewPasswordDataComponent<Errors>(
    context: ComponentContext,
    private val errors: Errors,
) : NewMemoDataComponent,
    ComponentContext by context
        where Errors : EmptyPasswordMessageOwner,
              Errors : PasswordsDontMatchMessageOwner {

    private val input =
        Input(
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