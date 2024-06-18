package cdu278.repetition.item

import cdu278.repetition.Repetition
import cdu278.repetition.RepetitionState
import cdu278.repetition.RepetitionType

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