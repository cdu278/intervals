package midget17468.memo.model.input

import kotlinx.serialization.Serializable

@Serializable
data class ConfirmedPasswordInput(
    val password: String = "",
    val passwordConfirmation: String = "",
)