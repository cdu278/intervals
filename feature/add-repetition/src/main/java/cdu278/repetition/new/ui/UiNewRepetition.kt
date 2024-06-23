package cdu278.repetition.new.ui

import cdu278.repetition.RepetitionType
import cdu278.repetition.new.data.ui.UiNewRepetitionData
import cdu278.ui.input.UiInput

data class UiNewRepetition(
    val label: UiInput<String>,
    val type: RepetitionType,
    val data: UiNewRepetitionData<Error>,
    val hint: UiInput<String>,
    val saving: Boolean = false,
    val error: Error? = null,
) {

    enum class Error {

        EmptyLabel,
        LabelExists,
        EmptyPassword,
        PasswordsDontMatch,
    }
}