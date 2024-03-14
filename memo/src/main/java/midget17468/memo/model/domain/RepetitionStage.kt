package midget17468.memo.model.domain

import kotlinx.serialization.Serializable
import kotlin.time.Duration
import kotlin.time.DurationUnit
import kotlin.time.toDuration

@Serializable
class RepetitionStage(
    private val value: Int
) {

    val space: Duration
        get() = (value + 1).toDuration(DurationUnit.DAYS)

    fun next(): RepetitionStage {
        return RepetitionStage(value + 1)
    }

    companion object {

        val Initial: RepetitionStage
            get() = RepetitionStage(value = 0)
    }
}