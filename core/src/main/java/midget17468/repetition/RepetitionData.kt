package midget17468.repetition

import kotlinx.serialization.Serializable

@Serializable
sealed interface RepetitionData {

    @Serializable
    class Hashed(
        val hash: String,
    ) : RepetitionData
}