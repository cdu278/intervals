package cdu278.repetition.info.ui

import cdu278.repetition.RepetitionType
import cdu278.repetition.item.RepetitionItem

internal data class UiRepetitionInfo(
    val id: Long,
    val label: String,
    val type: RepetitionType,
) {

    constructor(item: RepetitionItem) :
            this(
                item.id,
                item.label,
                item.type,
            )
}