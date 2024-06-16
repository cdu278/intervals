package midget17468.repetition.item

import midget17468.repetition.Repetition
import midget17468.repetition.RepetitionState
import midget17468.repetition.RepetitionType

class RepetitionItem(
    val id: Long,
    val type: RepetitionType,
    val label: String,
    val repetitionState: RepetitionState,
) {

    companion object {

        fun Repetition.asItem(): RepetitionItem {
            return RepetitionItem(
                id,
                type,
                label,
                state,
            )
        }
    }
}