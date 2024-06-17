package midget17468.repetition.matching

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import midget17468.hash.s.Hashes
import midget17468.repetition.RepetitionData
import midget17468.repetition.RepetitionData.Hashed
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

class RepetitionDataMatching(
    private val hashes: Hashes,
) {

    @OptIn(ExperimentalEncodingApi::class)
    suspend infix fun RepetitionData.matches(data: String): Boolean {
        return when (val thisData = this) {
            is Hashed ->
                withContext(Dispatchers.Default) {
                    val thisHash = thisData.hash.value
                    val salt = Base64.decode(thisData.hash.salt)
                    val thatHash = hashes.of(data, salt).value
                    thisHash == thatHash
                }
        }
    }
}