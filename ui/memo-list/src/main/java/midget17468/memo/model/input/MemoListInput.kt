package midget17468.memo.model.input

import kotlinx.serialization.Serializable

@Serializable
data class MemoListInput(
    val idOfExpanded: Int? = null,
)