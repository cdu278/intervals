package midget17468.hash.s

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import midget17468.hash.s.repository.HashesRepository
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.PBEKeySpec
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

class Hashes(
    private val repository: HashesRepository
) {

    @OptIn(ExperimentalEncodingApi::class)
    suspend fun of(data: String): String {
        return withContext(Dispatchers.Default) {
            val password = data.toCharArray()
            val iterationCount = 65536
            val keyLength = 128
            SecretKeyFactory
                .getInstance("PBKDF2WithHmacSHA1")
                .generateSecret(PBEKeySpec(password, repository.salt(), iterationCount, keyLength))
                .encoded
                .let { Base64.encode(it) }
        }
    }
}