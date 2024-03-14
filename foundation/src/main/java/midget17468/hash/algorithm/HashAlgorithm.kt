package midget17468.hash.algorithm

import kotlinx.serialization.Serializable

@Serializable
sealed interface HashAlgorithm {

    suspend fun String.hash(): String

    @Serializable
    data object Simple : HashAlgorithm {

        override suspend fun String.hash(): String {
            return hashCode().toString()
        }
    }
}