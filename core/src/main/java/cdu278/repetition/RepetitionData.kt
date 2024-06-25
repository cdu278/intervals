package cdu278.repetition

import androidx.annotation.Keep
import kotlinx.serialization.Serializable
import cdu278.hash.Hash

@Serializable
@Keep
sealed interface RepetitionData {

    @Serializable
    @Keep
    class Hashed(
        @Keep
        val hash: Hash,
    ) : RepetitionData
}