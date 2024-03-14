package midget17468.memo.model.input

import kotlinx.serialization.Serializable

@Serializable
sealed interface RepetitionInput {

    @Serializable
    data class Checking(
        val data: String = "",
        val hintShown: Boolean = false,
    ) : RepetitionInput

    @Serializable
    data object Forgotten : RepetitionInput
}