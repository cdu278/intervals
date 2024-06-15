package midget17468.password.ui

import kotlinx.serialization.Serializable

@Serializable
data class ConfirmedPasswordInput(
    val password: String = "",
    val passwordConfirmation: String = "",
)