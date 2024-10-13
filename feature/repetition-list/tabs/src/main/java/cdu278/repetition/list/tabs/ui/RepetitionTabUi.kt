package cdu278.repetition.list.tabs.ui

import cdu278.repetition.RepetitionType
import cdu278.ui.input.UiToggleable

internal data class RepetitionTabUi(
    val type: RepetitionType?,
    val selected: UiToggleable,
)