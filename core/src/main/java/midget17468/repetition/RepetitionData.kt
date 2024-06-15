package midget17468.repetition

import kotlinx.serialization.Serializable
import midget17468.hash.algorithm.HashAlgorithm

@Serializable
sealed interface RepetitionData {

    suspend fun matches(value: String): Boolean

    @Serializable
    class Hashed(
        private val hash: String,
        private val algorithm: HashAlgorithm,
    ) : RepetitionData {

        override suspend fun matches(value: String): Boolean {
            return with(algorithm) { value.hash() } == hash
        }
    }
}