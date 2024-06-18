package midget17468.hash.s

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import midget17468.hash.Hash
import java.security.SecureRandom
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.PBEKeySpec

class Hashes(
    private val base64: ByteArray.() -> String
) {

    suspend fun of(data: String, salt: ByteArray): Hash {
        return withContext(Dispatchers.Default) {
            val password = data.toCharArray()
            val iterationCount = 65536
            val keyLength = 128
            SecretKeyFactory
                .getInstance("PBKDF2WithHmacSHA1")
                .generateSecret(PBEKeySpec(password, salt, iterationCount, keyLength))
                .encoded
                .let {
                    Hash(
                        value = it.base64(),
                        salt = salt.base64(),
                    )
                }
        }
    }

    suspend fun of(data: String): Hash = of(data, generatedSalt())

    private suspend fun generatedSalt(): ByteArray {
        return withContext(Dispatchers.Default) {
            ByteArray(16)
                .also { SecureRandom().nextBytes(it) }
        }
    }
}