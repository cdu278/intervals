package midget17468.repetition

import kotlinx.serialization.Serializable
import midget17468.hash.Hash

@Serializable
sealed interface RepetitionData {

    @Serializable
    class Hashed(
        val hash: Hash,
    ) : RepetitionData
}