package midget17468.memo.model.ui

import midget17468.model.ui.UiInput

data class UiNewPasswordData(
    val password: UiInput<String>,
    val passwordConfirmation: UiInput<String>
)