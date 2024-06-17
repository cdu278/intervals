package midget17468.repetition.new.ui

import midget17468.repetition.new.data.ui.UiNewRepetitionData
import midget17468.ui.input.UiInput

data class UiNewRepetition(
    val label: UiInput<String>,
    val data: UiNewRepetitionData,
    val hint: UiInput<String>,
    val saving: Boolean = false,
    val error: String? = null,
)