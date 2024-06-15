package midget17468.repetition.stage

import kotlinx.serialization.Serializable

@Serializable
class RepetitionStage(
    val value: Int
) {

    fun next(): RepetitionStage {
        return RepetitionStage(value + 1)
    }

    companion object {

        val Initial: RepetitionStage
            get() = RepetitionStage(value = 0)
    }
}