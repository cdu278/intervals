package cdu278.repetition.new.ui

import cdu278.repetition.new.data.ui.UiNewRepetitionData
import cdu278.ui.input.UiInput

data class UiNewRepetition(
    val label: UiInput<String>,
    val data: UiNewRepetitionData,
    val hint: UiInput<String>,
    val saving: Boolean = false,
    val error: String? = null,
)