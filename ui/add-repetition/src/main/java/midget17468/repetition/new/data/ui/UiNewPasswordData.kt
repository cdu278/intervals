package midget17468.repetition.new.data.ui

import midget17468.ui.input.UiInput

data class UiNewPasswordData(
    val password: UiInput<String>,
    val passwordConfirmation: UiInput<String>
)