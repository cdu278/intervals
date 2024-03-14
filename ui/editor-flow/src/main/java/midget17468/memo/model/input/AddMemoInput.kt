package midget17468.memo.model.input

import kotlinx.serialization.Serializable

@Serializable
data class AddMemoInput(
    val label: String = "",
    val hint: String = "",
)