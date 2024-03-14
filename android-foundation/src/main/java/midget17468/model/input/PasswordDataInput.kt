package midget17468.model.input

import kotlinx.serialization.Serializable
import midget17468.input.Input
import midget17468.input.prop

@Serializable
data class PasswordDataInput(
    val password: String = "",
    val passwordConfirmation: String = ""
)

val Input<PasswordDataInput>.password: Input<String>
    get() = prop(PasswordDataInput::password) { copy(password = it) }

val Input<PasswordDataInput>.passwordConfirmation: Input<String>
    get() = prop(PasswordDataInput::passwordConfirmation) { copy(passwordConfirmation = it) }