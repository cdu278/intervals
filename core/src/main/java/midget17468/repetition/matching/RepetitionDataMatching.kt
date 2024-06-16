package midget17468.repetition.matching

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import midget17468.hash.s.Hashes
import midget17468.repetition.RepetitionData
import midget17468.repetition.RepetitionData.Hashed

class RepetitionDataMatching(
    private val hashes: Hashes,
) {

    suspend infix fun RepetitionData.matches(data: String): Boolean {
        return when (val thisData = this) {
            is Hashed ->
                withContext(Dispatchers.Default) {
                    hashes.of(data) == thisData.hash
                }
        }
    }
}