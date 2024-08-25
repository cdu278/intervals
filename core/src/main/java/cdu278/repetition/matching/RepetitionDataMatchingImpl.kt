package cdu278.repetition.matching

import cdu278.computable.Computable
import cdu278.hash.s.Hashes
import cdu278.repetition.RepetitionData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

fun RepetitionDataMatching(
    hashes: Hashes,
): RepetitionDataMatching {
    return RepetitionDataMatchingImpl(hashes)
}

private class RepetitionDataMatchingImpl(
    private val hashes: Hashes,
) : RepetitionDataMatching {

    @OptIn(ExperimentalEncodingApi::class)
    override suspend infix fun RepetitionData.matches(data: Computable<String>): Boolean {
        return when (val thisData = this) {
            is RepetitionData.Hashed ->
                withContext(Dispatchers.Default) {
                    val thisHash = thisData.hash.value
                    val salt = Base64.decode(thisData.hash.salt)
                    val thatHash = hashes.of(data, salt).value
                    thisHash == thatHash
                }
        }
    }
}