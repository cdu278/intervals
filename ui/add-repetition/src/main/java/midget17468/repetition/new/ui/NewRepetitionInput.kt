package midget17468.repetition.new.ui

import kotlinx.serialization.Serializable

@Serializable
data class NewRepetitionInput(
    val label: String = "",
    val hint: String = "",
)