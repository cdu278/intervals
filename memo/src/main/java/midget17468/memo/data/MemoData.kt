package midget17468.memo.data

import kotlinx.serialization.Serializable
import midget17468.hash.algorithm.HashAlgorithm

@Serializable
sealed interface MemoData {

    suspend fun matches(value: String): Boolean

    @Serializable
    class Hashed(
        private val hash: String,
        private val algorithm: HashAlgorithm,
    ) : MemoData {

        override suspend fun matches(value: String): Boolean {
            return with(algorithm) { value.hash() } == hash
        }
    }
}