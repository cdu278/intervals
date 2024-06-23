package cdu278.repetition.new.flow.type.selection.ui

import cdu278.repetition.RepetitionType
import cdu278.ui.action.UiAction

data class UiRepetitionTypeSelection(
    val items: List<Item>
) {

    data class Item(
        val type: RepetitionType,
        val choose: UiAction,
    )
}