package midget17468.model.input

import kotlinx.serialization.Serializable
import midget17468.state.State
import midget17468.state.prop

@Serializable
data class PasswordDataInput(
    val password: String = "",
    val passwordConfirmation: String = ""
)

val State<PasswordDataInput>.password: State<String>
    get() = prop(PasswordDataInput::password) { copy(password = it) }

val State<PasswordDataInput>.passwordConfirmation: State<String>
    get() = prop(PasswordDataInput::passwordConfirmation) { copy(passwordConfirmation = it) }