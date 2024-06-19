package cdu278.repetition.new.data.ui

import cdu278.ui.input.UiInput

data class UiNewPasswordData(
    val password: UiInput<String>,
    val passwordConfirmation: UiInput<String>
)