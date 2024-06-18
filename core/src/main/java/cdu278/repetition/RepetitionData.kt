package cdu278.repetition

import kotlinx.serialization.Serializable
import cdu278.hash.Hash

@Serializable
sealed interface RepetitionData {

    @Serializable
    class Hashed(
        val hash: Hash,
    ) : RepetitionData
}